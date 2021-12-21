package test.bootstrap.boot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.file.Files;
import java.util.Properties;
import java.util.UUID;

import org.junit.Test;

import com.cbt.agent.bootstrap.AgentBootMain;
import com.cbt.agent.bootstrap.CbtSessionInfo;
import com.cbt.agent.bootstrap.JaxbUtil;
import com.cbt.agent.bootstrap.UtilEncryption;

public class AgentBootMainTest {
    @Test
    public void testPremain() throws IOException {
        Instrumentation inst = (Instrumentation) Proxy.newProxyInstance(AgentBootMainTest.class.getClassLoader(),
                new Class[]{Instrumentation.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
        String serviceUrl = createSession().toString();
        String fastjson = "C:/Users/Administrator/.m2/repository/com/alibaba/fastjson/1.1.23/fastjson-1.1.23.jar";
        String agentArgs = "open=true,appId=test_team,proKey=test0123,proSecret=00215,serviceUrl=" + serviceUrl
                + ",fastJson.url=" + fastjson;
        AgentBootMain.premain(agentArgs, inst);
    }

    @Test
    public void TEst_启动成功() {
        Instrumentation inst = (Instrumentation) Proxy.newProxyInstance(AgentBootMainTest.class.getClassLoader(),
                new Class[]{Instrumentation.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
        String serviceUrl = "http://192.168.17.200:8800/api?method=client.login";
        String fastjson = "/Users/tommy/git/cbt-agent/out/fastjson-1.1.23.jar";
        String agentArgs = "debug=true,open=true,appId=test_team,proKey=b71e2da2f7b74cba94ad008403ba594f,proSecret=79e7b7950b5940cf8fdc8ab40c434fe8,serviceUrl="
                + serviceUrl + ",fastJson.url=" + fastjson;
        AgentBootMain.premain(agentArgs, inst);
    }

    @Test
    public void TEst_登陆服务找不到项目Key() {
        Instrumentation inst = (Instrumentation) Proxy.newProxyInstance(AgentBootMainTest.class.getClassLoader(),
                new Class[] { Instrumentation.class }, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });

        String serviceUrl = "http://localhost:8080/api?method=client.login";
        String agentArgs = "open=true,appId=test_team,proKey=1b71e2da2f7b74cba94ad008403ba594f,proSecret=79e7b7950b5940cf8fdc8ab40c434fe8,serviceUrl="
                + serviceUrl;
        AgentBootMain.premain(agentArgs, inst);
    }

    @Test
    public void TEst_登陆服务找不到签名错误() {
        Instrumentation inst = (Instrumentation) Proxy.newProxyInstance(AgentBootMainTest.class.getClassLoader(),
                new Class[] { Instrumentation.class }, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });

        String serviceUrl = "http://localhost:8080/api?method=client.login";
        String agentArgs = "open=true,appId=test_team,proKey=b71e2da2f7b74cba94ad008403ba594f,proSecret=179e7b7950b5940cf8fdc8ab40c434fe8,serviceUrl="
                + serviceUrl;
        AgentBootMain.premain(agentArgs, inst);
    }

    @Test
    public void testCreateSession() throws IOException {
        URL url = createSession();
        System.out.println(url);
    }

    public URL createSession() throws IOException {
        URL result;
        URL url = new URL("file:/Users/tommy/git/cbt-agent/out/cbt-agent-base-1.0-SNAPSHOT.jar");
        InputStream input = url.openStream();
        byte[] bytes = sun.misc.IOUtils.readFully(input, -1, false);
        input.close();
        String md5sign = UtilEncryption.md5(bytes);
        CbtSessionInfo session = new CbtSessionInfo();
        session.setProKey("test0123");
        session.setClientVersion("0.1");
        session.setLoginTime(System.currentTimeMillis());
        session.setSessionId(UUID.randomUUID().toString());
        session.setClientMd5(md5sign);
        session.setClientUploadUrls(new String[]{url.toString()});
        Properties config = new Properties();
        config.put("testP", "testV");
        session.setConfigs(config);
        session.setAppId("test_team");
        String xml = JaxbUtil.convertToXml(session);
        File f = new File("/Users/tommy/git/cbt-agent/out/mockSession.xml");
        Files.write(f.toPath(), xml.getBytes());
        System.out.println(xml);
        result = f.toURI().toURL();
        return result;
    }

    @Test
    public void clientMd5Create() throws IOException {
        String file="C:\\Users\\Administrator\\Desktop\\cbt-agent-base-V1.1.3.jar";
        byte[] bytes = Files.readAllBytes(new File(file).toPath());
        String md5Text = UtilEncryption.md5(bytes);
        System.out.println(md5Text);
    }

}
