package test.collects.servlet;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cbt.agent.core.*;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceFrom;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceRequest;
import com.cbt.agent.trace.TraceSession;

public class BasiceTest {
    public static TraceSession session;

    public final TraceContext context;

    protected   AgentLoader loader;
    List<SrcTemplate> srcTemplates;
    public BasiceTest() {
        loader = new AgentLoader();
        srcTemplates = new ArrayList<>();
        SrcTemplate returnTemplate = new SrcTemplate("returnTemplate", BasiceTest.return_Src_Template);
        SrcTemplate voidTemplate = new SrcTemplate("voidTemplate", BasiceTest.void_src_template);
        srcTemplates.add(returnTemplate);
        srcTemplates.add(voidTemplate);

        /*try {
            loader.loadItemByJar(new File(System.getProperty("user.dir") + "/target/cbt-agent-collects-1.0-SNAPSHOT.jar").toURL());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
//        AgentFinal.LOCAL_CONFIG.setProperty( AgentFinal.FAST_JSON_URL,"file:/Users/tommy/git/cbt-agent/out/fastjson-1.1.23.jar");

        // 默认构建一个TraceContext上下文
        Properties p = new Properties();
        p.setProperty("trace.upload.way", "logfile");
        p.setProperty("trace.open", "true");
        p.setProperty("trace.upload.threads", "3");
        p.setProperty("server.appId", "66");
        context = new TraceContext(p);
        TraceContext.setDefaultContext(context);
        beginTrace();
    }

    protected String getSrc(String templateName){
        for (SrcTemplate srcTemplate : srcTemplates) {
            if (srcTemplate.getName().equals(templateName)) {
                return srcTemplate.getValue();
            }
        }
        return null;
    }


    public ArrayList<TraceNode> newMockTraceNodes(String traceId) {
        ArrayList nodes = new ArrayList<>();
//        String traceId = UUID.randomUUID().toString().replaceAll("-","");
//        System.out.println(traceId);
        {
            TraceNode traceNode = new TraceNode();
            traceNode.setTraceId(traceId);
            traceNode.setResultState("ok");
            traceNode.setNodeType("http");
            traceNode.setBeginTime(System.currentTimeMillis());
            traceNode.setRpcId("0");
            traceNode.setAppId("66");
            traceNode.setServicePath("zookper://192.168.17.105:2181");
            traceNode.setServiceName("com.cbt.libService");
            traceNode.setBeginTime(System.currentTimeMillis());
            traceNode.setEndTime(System.currentTimeMillis() + 200);
            traceNode.setAppDetail("empty");
            nodes.add(traceNode);
        }
        for (int i = 1; i < 5; i++) {
            TraceNode traceNode = new TraceNode();
            traceNode.setTraceId(traceId);
            traceNode.setResultState("ok");
            traceNode.setNodeType("dubbo rpc");
            traceNode.setBeginTime(System.currentTimeMillis());
            traceNode.setRpcId("0." + i);
            traceNode.setAppId("66");
            traceNode.setServiceName("com.cbt.libService");
            traceNode.setBeginTime(System.currentTimeMillis());
            traceNode.setEndTime(System.currentTimeMillis() + 200);
            traceNode.setServicePath("zookper://192.168.17.105:2181");
            nodes.add(traceNode);
        }
        {
            TraceNode traceNode = new TraceNode();
            traceNode.setTraceId(traceId);
            traceNode.setNodeType("dubbo rpc");
            traceNode.setResultState("ok");
            traceNode.setBeginTime(System.currentTimeMillis());
            traceNode.setRpcId("0.2");
            traceNode.setAppId("66");
            traceNode.setServiceName("com.cbt.libService");
            traceNode.setBeginTime(System.currentTimeMillis());
            traceNode.setServicePath("zookper://10.200.51.93:2181");
            traceNode.setEndTime(System.currentTimeMillis() + 200);
            nodes.add(traceNode);
        }
        {
            TraceNode traceNode = new TraceNode();
            traceNode.setTraceId(traceId);
            traceNode.setNodeType("dubbo rpc");
            traceNode.setResultState("ok");
            traceNode.setBeginTime(System.currentTimeMillis());
            traceNode.setRpcId("0.2.1");
            traceNode.setAppId("65");
            traceNode.setServicePath("zookper://192.168.17.105:2181");
            traceNode.setServiceName("com.cbt.libService");
            traceNode.setBeginTime(System.currentTimeMillis());
            traceNode.setEndTime(System.currentTimeMillis() + 200);
            nodes.add(traceNode);
        }
        return nodes;
    }

    public TraceFrom newMockTraceFrom(String traceId) {
        TraceFrom info = new TraceFrom();
        info.setTraceId(traceId);
        info.setClientIp("123.123.4.106");
        info.setClientId("19");
        info.setCreateTime(System.currentTimeMillis());
        info.setSource("H5");
        info.setTraceLevel("info");
        info.setAccessPath("http://120.76.224.136:10000/457845748555.html");
        info.setRefererPath("http://120.76.224.136:10000");
        info.setDebugId("cbt_debug_c9f011e06c4342c6b9a35cfd40f92de7");
        return info;
    }

    public void beginTrace() {
        TraceRequest request = new TraceRequest();
        Properties properties = new Properties();
        request.setTraceId(TraceSession.createTraceId());
        request.setParentRpcId("0");
        request.setProperties(properties);
        session = TraceContext.getDefault().openTrace(request);
    }

    public void endTrace() {
        TraceContext.getDefault().closeTrace(session);
    }

    public List<AgentItemSource> loadAgentItems(String className, String handClass, String srcTemplate, String beforeSrc,
                                                String afterSrc, MethodInfo... methods) throws IOException, CannotCompileException, NotFoundException {
        List<AgentItemSource> list = new ArrayList();
        for (MethodInfo methodInfo : methods) {
            AgentItemSource s = new AgentItemSource();
            s.setTargetClassName(className);
            s.setHandleClassName(handClass);
            s.setBeforeSrc(beforeSrc);
            s.setAfterSrc(afterSrc);
            s.setMethodInfo(methodInfo);
            s.setSrcTemplate(srcTemplate);
            s.setWay(AgentFinal.AgentWay.trace);
            list.add(s);
        }
        return list;
    }


    public static final String return_Src_Template =
            "{\n"
                    + "com.cbt.agent.collect.InParams _param=new com.cbt.agent.collect.InParams();\n"
                    + "        com.cbt.agent.collect.OutResult _result=new com.cbt.agent.collect.OutResult();\n"
                    + "        _param.args=$args;\n"
                    + "        _result.args=$args;\n"
                    + "        _param.className=\"${target.className}\";\n"
                    + "        _param.methodName=\"${target.methodName}\";\n"
                    + "        _result.className=\"${target.className}\";\n"
                    + "        _result.methodName=\"${target.methodName}\";\n"
                    + "        _param.paramTypes=\"${target.paramTypes}\".split(\",\");\n"
                    + "        _param.paramTypes=\"${target.paramTypes}\".split(\",\");\n"
                    + "${beforeSrc}\n"
                    + "        com.cbt.agent.collect.CollectHandle handle=com.cbt.agent.collect.CollectHandleBeanFactory.getBean(${handle.className}.class);\n"
                    + "        com.cbt.agent.collect.Event event = handle.invokerBefore(null,_param);\n"
                    + "        try {\n"
                    + "            _result.result=($w)${target.methodName}$agent($$);\n"
                    + "        } catch (Throwable e) {\n"
                    + "            _result.error=e;\n"
                    + "            throw e;\n"
                    + "        }finally{\n"
                    + "${afterSrc}\n"
                    + "            handle.invokerAfter(event, _result);\n"
                    + "        }\n"
                    + "        return ($r) _result.result;\n"
                    + "}\n";

    public static final String void_src_template  = "{\n" +
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
            "            ${target.methodName}$agent($$);\n" +
            "        } catch (Throwable e) {\n" +
            "            _result.error=e;\n" +
            "            throw e;\n" +
            "        }finally{\n" +
            "${afterSrc}\n" +
            "            handle.invokerAfter(event, _result);\n" +
            "        }\n" +
            "}\n";

    public static void main(String[] args) throws Exception {
        new BasiceTest();
    }
}
