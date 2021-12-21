package test.base.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cbt.agent.common.util.JaxbUtil;
import com.cbt.agent.core.AgentFinal.AgentWay;
import com.cbt.agent.core.AgentItemSource;
import com.cbt.agent.core.AgentPlugin;
import com.cbt.agent.core.MethodInfo;
import com.cbt.agent.core.SrcTemplate;

/**
 * Created by tommy on 16/11/4.
 */
public class JaxbUtilTest {
    static String  tempValue=
            "\n{\n" +
            "com.cbt.agent.collect.InParams _param=new com.cbt.agent.collect.InParams();\n" +
            "        com.cbt.agent.collect.OutResult _result=new com.cbt.agent.collect.OutResult();\n" +
            "        _param.args=$args;\n" +
            "        _result.args=$args;\n" +
            "        _param.className=\"${target.className}\";\n" +
            "        _param.methodName=\"${target.methodName}\";\n" +
            "        _result.className=\"${target.className}\";\n" +
            "        _result.methodName=\"${target.methodName}\";\n" +
            "        _param.paramTypes=\"${target.paramTypes}\".split(\",\");\n" +
            "        _param.paramTypes=\"${target.paramTypes}\".split(\",\");\n" +
            "${beforeSrc}\n" +
            "        com.cbt.agent.collect.CollectHandle handle=com.cbt.agent.collect.CollectHandleBeanFactory.getBean(${handle.className}.class);\n" +
            "        com.cbt.agent.collect.Event event = handle.invokerBefore(null,_param);\n" +
            "        try {\n" +
            "            _result.result=($w)${target.methodName}$agent($$);\n" +
            "        } catch (Throwable e) {\n" +
            "            _result.error=e;\n" +
            "            throw e;\n" +
            "        }finally{\n" +
            "${afterSrc}\n" +
            "            handle.invokerAfter(event, _result);\n" +
            "        }\n" +
            "        return ($r) _result.result;\n" +
            "}\n";
    @Test
    public void testConvertToXml() throws Exception {
        AgentPlugin plugin=new AgentPlugin();
        plugin.setDescribe("测试插件");

        plugin.setTemplates(new SrcTemplate[]{new SrcTemplate("common",tempValue)});
        List<AgentItemSource> list=new ArrayList<>();
        plugin.setAgentItems(list);
        AgentItemSource obj = new AgentItemSource();
        obj.setAfterSrc("sysout_after_src");
        obj.setTargetClassName("javax.http.servlet");
        MethodInfo methodinfo = new MethodInfo();
        methodinfo.setReturnType("void");
        methodinfo.setMethodName("service");
        methodinfo.setParamTypes(new String[]{"httprequest", "httpresponse"});
        obj.setMethodInfo(methodinfo);
        obj.setSrcTemplate("common");

        obj.setWay(AgentWay.trace);
        list.add(obj);
        String xml = JaxbUtil.convertToXml(plugin);
        System.out.println(xml);
    }
}