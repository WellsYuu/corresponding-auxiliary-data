package com.cbt.agent.collects.httpClient;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.common.util.NetUtils;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceSession;
import com.cbt.agent.collect.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by tommy on 16/7/23.
 */
public class HttpURLConnectionHandle implements CollectHandle {
    private  final Logger logger = LoggerFactory.getLogger(HttpURLConnectionHandle.class);

    @Override
    public Event invokerBefore(Event event, InParams in) {
        if(TraceContext.getDefault().getCurrentSession()==null){
            return null;
        }

        if(in.methodName.equals("AgentHttpUrlConnection")){
           return  agentHttpUrlConnectionBefore(in);
        }else if (in.methodName.equals("connect")) {
            connectBefore(event,in);
        }
        return null;
    }

    @Override
    public void invokerAfter(Event event, OutResult out) {
        TraceSession currentSession = TraceContext.getDefault().getCurrentSession();
        if (currentSession == null) {
            return;
        } else if (event == null || !event.isActive()) {
            return;
        }

        if (out.methodName.equals("connect")) {
            connectAfter(event,out);
        }else if (out.methodName.equals("disconnect")) {
            disconnectAfter(event,out);
        }
    }


    private Event agentHttpUrlConnectionBefore(InParams in) {
        TraceSession currentSession = TraceContext.getDefault().getCurrentSession();
        Event event=new Event(getEventType(),true);
        event.setDestoryMethod("java.net.HttpURLConnection","disconnect",null); //设置销毁方法
        TraceNode node=new TraceNode();
        node.setTraceId(currentSession.getTraceId());
        node.setRpcId(currentSession.getNextRpcId());
        node.setBeginTime(System.currentTimeMillis());
        node.setNodeType("http_call");
        URL url= (URL) in.args[1];
        node.setServicePath(url.toString());
        node.setServiceName("http_call");
        node.setAddressIp(url.getHost());
        node.setFromIp(NetUtils.getLocalHost());
        event.setData(new Object[]{(HttpURLConnection)in.args[0],node});
        AgentHttpUrlConnection agentConn= (AgentHttpUrlConnection) in.getOthers().get("agentHttpConn");
        agentConn.setEvent(event);
        return event;
    }

    private void connectBefore(Event event, InParams in) {
        Object [] args = (Object[]) event.getData();
        TraceNode node = (TraceNode) args[1];
        HttpURLConnection conn = (HttpURLConnection) args[0];
        // 需要在执行完成之后在读取
//        Map<String, List<String>> headers = conn.getHeaderFields();
        Map<String, List<String>> requestProperties = conn.getRequestProperties();
        String requestMethod = conn.getRequestMethod();
        StringBuffer sbuffer=new StringBuffer();
//        sbuffer.append("headers:"+headers.toString());
        sbuffer.append("    requestProperties:"+requestProperties.toString());
        sbuffer.append("    method:"+requestMethod);
        node.setInParam(sbuffer.toString());
    }

    private void connectAfter(Event event, OutResult out) {
        Object [] args = (Object[]) event.getData();
        TraceNode node = (TraceNode) args[1];
        if (out.error != null) {
            node.setResultState("fail");
            node.setErrorMessage(out.error.getMessage());
            ByteArrayOutputStream output=new ByteArrayOutputStream();
            out.error.printStackTrace(new PrintStream(output));
            node.setErrorStack(output.toString());
            node.setEndTime(System.currentTimeMillis());
            TraceContext.getDefault().getCurrentSession().addNode(node);
            event.close();
        }
    }

    private void disconnectAfter(Event event, OutResult out) {
        Object [] args = (Object[]) event.getData();
        TraceNode node = (TraceNode) args[1];
        HttpURLConnection conn = (HttpURLConnection) args[0];
        AgentHttpUrlConnection agentConn= (AgentHttpUrlConnection) out.others.get("agentHttpConn");
        String inParams=new String(agentConn.getOutputBytes());
        String params=node.getInParam()+"    params："+inParams;
        node.setInParam(params);

        try {
            node.setOutParam(new String(agentConn.getInputBytes()));
            node.setResultState(conn.getResponseCode() + conn.getResponseMessage());
        } catch (IOException e) {
            logger.error("Http getResponseCode get Error", e);
        }
        node.setEndTime(System.currentTimeMillis());
        TraceContext.getDefault().getCurrentSession().addNode(node);
//        event.close();
    }



    @Override
    public EventType getEventType() {
        return EventType.http_api;
    }
}
