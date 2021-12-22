package test.collects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import javassist.CannotCompileException;
import javassist.NotFoundException;

import com.cbt.agent.common.util.JaxbUtil;
import com.cbt.agent.core.AgentItemSource;
import com.cbt.agent.core.AgentPlugin;
import com.cbt.agent.core.MethodInfo;
import com.cbt.agent.core.SrcTemplate;
import org.junit.Test;

/**
 * Created by tommy on 16/10/22.
 */
public class CollectItemBuildFactory extends BasiceTest {

    public static void main(String[] args) {
        try {
            new CollectItemBuildFactory().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 生成agentItem 并以json 形式保存至文件
    public void build() throws Exception {
        String out_file = System.getProperty("user.dir") + "/agent-collects/src/main/resources/agentConfig/agent.xml";
        File file = new File(out_file);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }


        List<AgentItemSource> list = new ArrayList();
        list.addAll(createJdbcItems());
//        list.addAll(createServletItems());
        list.addAll(createDubboConsumerItems());
        list.addAll(createDubboProviderItems());
        list.addAll(createRedisItems());

        /**
         * java 序列化并转成base64
         */
        /*ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream objout = new ObjectOutputStream(output);
        objout.writeObject(list);
        String str = new sun.misc.BASE64Encoder().encode(output.toByteArray());*/
        /**
         * 序列化成json 串
         */
        // str=JSON.toJSONString(list);

        /**
         * 转换成xml
         */

        AgentPlugin plugin = new AgentPlugin();
        plugin.setDescribe("基础采集器");
        plugin.setAgentItems(list);
        SrcTemplate returnTemplate = new SrcTemplate("returnTemplate", BasiceTest.return_Src_Template);
        SrcTemplate voidTemplate = new SrcTemplate("voidTemplate", BasiceTest.void_src_template);
        plugin.setTemplates(new SrcTemplate[]{returnTemplate, voidTemplate});
        String str = JaxbUtil.convertToXml(plugin);

        System.out.println(str);
        FileOutputStream fileout = new FileOutputStream(file);
        fileout.write(str.getBytes());
        fileout.flush();
        fileout.close();
        System.out.println("agent item 生成成功!" + out_file);
    }

    /***
     * encode by Base64
     */
    public static String encodeBase64(byte[] input) throws Exception {
        com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(input);
        Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod = clazz.getMethod("encode", byte[].class);
        mainMethod.setAccessible(true);
        Object retObj = mainMethod.invoke(null, new Object[]{input});
        return (String) retObj;
    }

    /***
     * decode by Base64
     */
    public static byte[] decodeBase64(String input) throws Exception {
        Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod = clazz.getMethod("decode", String.class);
        mainMethod.setAccessible(true);
        Object retObj = mainMethod.invoke(null, input);
        return (byte[]) retObj;
    }


    public List<AgentItemSource> createJdbcItems() throws NotFoundException, CannotCompileException, IOException {
        String className = "com.mysql.jdbc.NonRegisteringDriver";
        String handClassName = "com.cbt.agent.collects.jdbc.JdbcDriverHandle";
        String method = "connect";
        MethodInfo info = new MethodInfo(className, method);
        info.setReturnType("java.sql.Connection");
        info.setParamTypes(new String[]{String.class.getName(), Properties.class.getName()});
        return loadAgentItems(className, handClassName, "returnTemplate", null, null, info);
    }

    public List<AgentItemSource> createServletItems() throws NotFoundException, CannotCompileException, IOException {
        String className = "javax.servlet.http.HttpServlet";
        String handClassName = "com.cbt.agent.collects.servlet.ServletHandle";
        String method = "service";
        MethodInfo info = new MethodInfo(className, method);
        info.setReturnType(void.class.getName());
        info.setParamTypes(new String[]{"javax.servlet.http.HttpServletRequest",
                "javax.servlet.http.HttpServletResponse"});
        return loadAgentItems(className, handClassName, "voidTemplate", null, null, info);
    }

    public List<AgentItemSource> createDubboConsumerItems() throws Exception {
        List<AgentItemSource> list = new ArrayList<>();
        {
            String className = "com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler";
            String handClassName = "com.cbt.agent.collects.dubbo.DubboConsumerRpcExceptionMonitorHandle";
            String method = "invoke";
            MethodInfo info = new MethodInfo(className, method);
            info.setReturnType(Object.class.getName());
            list.addAll(loadAgentItems(className, handClassName, "returnTemplate", "_param.put(\"invoker\",$0.invoker);", null, info));
        }
        {
            String className = "com.alibaba.dubbo.rpc.protocol.dubbo.filter.FutureFilter";
            String handClassName = "com.cbt.agent.collects.dubbo.DubboConsumerMonitorHandle";
            String method = "invoke";
            MethodInfo info = new MethodInfo(className, method);
            info.setReturnType("com.alibaba.dubbo.rpc.Result");
            list.addAll(loadAgentItems(className, handClassName, "returnTemplate", null, null, info));
        }
        return list;
    }

    public List<AgentItemSource> createDubboProviderItems() throws Exception {
        String className = "com.alibaba.dubbo.rpc.filter.EchoFilter";
        String handClassName = "com.cbt.agent.collects.dubbo.DubboProviderMonitorHandle";
        MethodInfo info = new MethodInfo();
        info.setClassName(className);
        info.setReturnType("com.alibaba.dubbo.rpc.Result");
        info.setMethodName("invoke");
        return loadAgentItems(className, handClassName, "returnTemplate", null, null, info);
    }

    public List<AgentItemSource> createRedisItems() throws Exception {
        String className = "redis.clients.jedis.Connection";
        String handClassName = "com.cbt.agent.collects.redis.RedisConnectionHandle";
        MethodInfo info = new MethodInfo();
        info.setClassName(className);
        info.setReturnType("redis.clients.jedis.Connection");
        info.setMethodName("sendCommand");
        info.setParamTypes(new String[]{"redis.clients.jedis.Protocol$Command",
                "[[B"});
        return loadAgentItems(className, handClassName, "returnTemplate", "_param.put(\"this\",$0);", null, info);
    }


    @Test
    public void agentXmlFormatTest() throws Exception {
        String out_file = System.getProperty("user.dir") + "/src/main/resources/agentConfig/agent.xml";
        byte[] bytes = Files.readAllBytes(new File(out_file).toPath());
        AgentPlugin agentPlugin = JaxbUtil.converyToJavaBean(
                new String(bytes, "UTF-8"), AgentPlugin.class);

        System.out.println(agentPlugin);
    }
}
