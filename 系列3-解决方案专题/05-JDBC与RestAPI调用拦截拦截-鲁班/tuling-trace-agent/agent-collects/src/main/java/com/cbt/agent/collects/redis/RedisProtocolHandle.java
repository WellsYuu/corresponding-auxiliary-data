package com.cbt.agent.collects.redis;

import com.cbt.agent.collect.*;
import com.cbt.agent.common.util.StringUtils;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceSession;
import org.springframework.util.Assert;
import redis.clients.jedis.Protocol.Command;
import redis.clients.util.SafeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RedisProtocolHandle implements CollectHandle {
    public static final String TARGET_CLASS = "redis.clients.jedis.Protocol";
    public static final String METHOD_NAME_SendCommand = "sendCommand";
    public static final String METHOD_NAME_Process = "process";
    private static final String REDIS_STATUS_FLAG = "redis_status_flag";


    private Event sendCommandBefore(Event event, InParams in) {
        // 开启一个新Redis 监听事件
        Event newEvent = new Event(EventType.redis, true);
        newEvent.setDestoryMethod(TARGET_CLASS, METHOD_NAME_Process, null);// 销毁事件
        String cmdText = "";
        if (in.args[1] instanceof Command) {
            Command command = (Command) in.args[1];
            cmdText = command.name();
        } else {
            cmdText = SafeEncoder.encode((byte[]) in.args[1]);
        }
        if (cmdText.equalsIgnoreCase("exists") || cmdText.equalsIgnoreCase("client") || cmdText.equalsIgnoreCase("ping")
                || cmdText.equalsIgnoreCase("select") || cmdText.equalsIgnoreCase("ok")) {
            // 注销当前事件
            newEvent.close();
            return null;
        }

        TraceSession session = TraceContext.getDefault().getCurrentSession();
        String rpcId = session.getNextRpcId();// 生成新的节点

        TraceNode node = new TraceNode();
        node.setTraceId(session.getTraceId());
        node.setRpcId(rpcId);
        node.setNodeType("redis");
        node.setBeginTime(System.currentTimeMillis());
        node.setServicePath("");
        node.setServiceName(cmdText);
        StringBuilder sb = new StringBuilder();
        if (in.args[2].getClass().isArray()) {
            for (Object param : (Object[]) in.args[2]) {
                if (param instanceof byte[]) {
                    sb.append(SafeEncoder.encode((byte[]) param) + "|");
                }
            }
        }
        node.setInParam(StringUtils.removeEnd(sb.toString(), "|"));
        newEvent.setData(node);
        return newEvent;
    }

    private void processAfter(Event event, OutResult out) {
        if (event == null || !event.isActive()) {
            return;
        }
        TraceNode node = (TraceNode) event.getData();
        TraceSession session = TraceContext.getDefault().getCurrentSession();


        if (out.error != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outStream);
            out.error.printStackTrace(printStream);
            node.setErrorStack(outStream.toString());
            node.setErrorMessage(out.error.getMessage());
        }
        node.setResultState(out.error == null ? "succeed" : "fail");
        node.setEndTime(System.currentTimeMillis());

        if (session.isDubug()) {
            Object result = out.result;
            if (result != null) {
                if (result instanceof byte[]) {
                    node.setOutParam(SafeEncoder.encode((byte[]) result));
                } else if (result instanceof Long) {
                    node.setOutParam(result.toString());
                }

                if (com.cbt.agent.common.util.StringUtils.isMessyCode(node.getOutParam())) {
                    node.setOutParam("Messy Code");
                }
            }
        }
        session.addNode(node);
    }

    @Override
    public Event invokerBefore(Event event, InParams in) {
        Assert.isTrue(TARGET_CLASS.equals(in.className));
        // 监控未开启
        if (TraceContext.getDefault().getCurrentSession() == null) {
            return null;
        }
        if (METHOD_NAME_SendCommand.equals(in.methodName)) {
            return sendCommandBefore(event, in);
        }
        return null;
    }

    @Override
    public void invokerAfter(Event event, OutResult out) {
        Assert.isTrue(TARGET_CLASS.equals(out.className));
        if (METHOD_NAME_Process.equals(out.methodName)) {
            processAfter(event, out);
        }
    }

    public EventType getEventType() {
        return EventType.redis;
    }
}
