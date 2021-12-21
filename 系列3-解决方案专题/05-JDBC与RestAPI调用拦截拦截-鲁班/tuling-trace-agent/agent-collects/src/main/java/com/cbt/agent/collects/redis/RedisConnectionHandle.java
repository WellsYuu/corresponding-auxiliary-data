package com.cbt.agent.collects.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

import com.cbt.agent.collect.CollectHandle;
import com.cbt.agent.collect.Event;
import com.cbt.agent.collect.EventType;
import com.cbt.agent.collect.InParams;
import com.cbt.agent.collect.MethodInfo;
import com.cbt.agent.collect.OutResult;
import com.cbt.agent.common.util.Assert;
import com.cbt.agent.common.util.JsonUtils;
import com.cbt.agent.common.util.NetUtils;
import com.cbt.agent.common.util.StringUtils;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceSession;

/**
 * Description: redis 通过连接来采集对应信息<br/>
 * 不支持二进制结果集的采集
 * 
 * @author zengguangwei@cbt.com
 * @version 1.0
 * @date: 2016年8月25日 上午11:17:23
 * @since JDK 1.7
 */
public class RedisConnectionHandle implements CollectHandle {
    // 默认追踪命令
    private final List<String> defaultCmds;
    public static final String target_class = "redis.clients.jedis.Connection";
    public static final String sendCommand = "sendCommand";
    public static final String getBulkReply = "getBulkReply";
    public static final String getMultiBulkReply = "getMultiBulkReply";
    public static final String getIntegerReply = "getIntegerReply";
    public static final String getIntegerMultiBulkReply = "getIntegerMultiBulkReply";
    public static final String getBinaryBulkReply = "getBinaryBulkReply";
    public static final String getBinaryMultiBulkReply = "getBinaryMultiBulkReply";
    public static final String getStatusCodeReply = "getStatusCodeReply";


    // 发送字符串命令方法
    private final MethodInfo sendCommandToString;
    private final MethodInfo sendCommandToBinary;

    public RedisConnectionHandle() {
        // 字符串cmd发送方法信息初始
        sendCommandToString = new MethodInfo(target_class, sendCommand, new String[] { "redis.clients.jedis.Protocol$Command",
                "[Ljava.lang.String;"});

        sendCommandToBinary = new MethodInfo(target_class, sendCommand, new String[] { "redis.clients.jedis.Protocol$Command",
                "[[B" }); // [[B 为byte[][]的类名

        defaultCmds = new ArrayList<>();
        defaultCmds.addAll(Arrays.asList("GET", "SET", "MGET", "HGET", "HMGET", "HGETALL"));
    }

    @Override
    public Event invokerBefore(Event event, InParams in) {
        Assert.isTrue(in.className.equals(target_class));
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        if (session == null) {
            return null;
        }

        // if (sendCommandToString.equals(in.getMethodInfo())) {
        // return sendCommandToStringBefore(in, session); // 发起事件
        // } else

        if (sendCommandToBinary.equals(in.getMethodInfo())) {
            return sendCommandToBinaryBefore(in, session);
        }
        return null;
    }

    private Event sendCommandToBinaryBefore(InParams in, TraceSession session) {
        Protocol.Command command = (Protocol.Command) in.args[0];
        if (command == null || defaultCmds.indexOf(command.name()) == -1) {
            return null;
        }
        // 构建Event 和Trace 节点
        Event event = new Event(getEventType(), true);
        event.setDestoryMethod(target_class, getBulkReply, null);
        TraceNode node = buildNode(in, session);
        // 构建参数信息
        node.setServiceName(command.name());
        byte[][] binaryParams = (byte[][]) in.args[1];
        String[] params = new String[binaryParams.length];
        for (int i = 0; i < binaryParams.length; i++) {
            params[i] = SafeEncoder.encode(binaryParams[i]);
            if (StringUtils.isMessyCode(params[i])) {
                params[i] = "content is Messy Code!";
            }
        }
        node.setInParam(JsonUtils.toJson(params, getClass().getClassLoader()));
        event.setData(node);
        return event;
    }

    @Override
    public void invokerAfter(Event event, OutResult out) {
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        if (session == null) {
            return;
        } else if (event == null || !event.isActive()) {
            return;
        }
        if (sendCommandToString.equals(out.getMethodInfo())) {
            sendCommandByStringAfter(event, out, session);
        } else if (sendCommandToBinary.equals(out.getMethodInfo())) {
            sendCommandToBinaryAfter(event, out, session);
        } else if (getBulkReply.equals(out.methodName)) {
            getBulkReplyAfter(event, out, session);
        } else if (getMultiBulkReply.equals(out.methodName)) {
            getMultiBulkReplyAfter(event, out, session);
        } else if (getIntegerReply.equals(out.methodName)) {
            getIntegerReplyAfter(event, out, session);
        } else if (getIntegerMultiBulkReply.equals(out.methodName)) {
            getIntegerMultiBulkReplyAfter(event, out, session);
        } else if (getStatusCodeReply.equals(out.methodName)) {
            getStatusCodeReplyAfter(event, out, session);
        }
    }

    private void getStatusCodeReplyAfter(Event event, OutResult out, TraceSession session) {
        getReplyAfter(event, out, session);
    }

    private void sendCommandToBinaryAfter(Event event, OutResult out, TraceSession session) {
        TraceNode node = (TraceNode) event.getData();
        if (out.error != null) {
            node.setErrorStack(JsonUtils.getStackTrace(out.error));
            node.setErrorMessage(out.error.getMessage());
            node.setResultState("fail");
        }
        node.setResultState(out.error == null ? "succeed" : "fail");
        node.setEndTime(System.currentTimeMillis());
        event.close();
        session.clearEvent(event);
        session.addNode(node);
    }

    private void getIntegerMultiBulkReplyAfter(Event event, OutResult out, TraceSession session) {
        getReplyAfter(event, out, session);
    }

    private void getIntegerReplyAfter(Event event, OutResult out, TraceSession session) {
        getReplyAfter(event, out, session);
    }

    private void getMultiBulkReplyAfter(Event event, OutResult out, TraceSession session) {
        getReplyAfter(event, out, session);
    }

    private void getBulkReplyAfter(Event event, OutResult out, TraceSession session) {
        getReplyAfter(event, out, session);
    }

    private void getReplyAfter(Event event, OutResult out, TraceSession session) {
        TraceNode node = (TraceNode) event.getData();
        if (out.error != null) {
            node.setErrorStack(JsonUtils.getStackTrace(out.error));
            node.setErrorMessage(out.error.getMessage());
            node.setResultState("fail");
        } else {
            node.setResultState("sucess");
            if (out.result != null)
                node.setOutParam(JsonUtils.toJson(out.result, getClass().getClassLoader()));
        }
        node.setEndTime(System.currentTimeMillis());
        event.close();
        session.clearEvent(event);
        session.addNode(node);
    }

    private void sendCommandByStringAfter(Event event, OutResult out, TraceSession session) {
        TraceNode node = (TraceNode) event.getData();
        if (out.error != null) {
            node.setErrorStack(JsonUtils.getStackTrace(out.error));
            node.setErrorMessage(out.error.getMessage());
            node.setResultState("fail");
            event.close();
            session.clearEvent(event);
            session.addNode(node);
        }
    }


    private Event sendCommandToStringBefore(InParams in, TraceSession session) {
        Protocol.Command command = (Protocol.Command) in.args[0];
        if (command == null || defaultCmds.indexOf(command.name()) == -1) {
            return null;
        }
        // 构建Event 和Trace 节点
        Event event = new Event(getEventType(), true);
        event.setDestoryMethod(target_class, getBulkReply, null);
        TraceNode node = buildNode(in, session);
        // 构建参数信息
        node.setServiceName(command.name());
        String[] params = (String[]) in.args[1];
        node.setInParam(JsonUtils.toJson(params, getClass().getClassLoader()));
        event.setData(node);
        return event;
    }

    private TraceNode buildNode(InParams in, TraceSession session) {
        TraceNode node = new TraceNode();
        node.setBeginTime(System.currentTimeMillis());
        node.setTraceId(session.getTraceId());
        node.setRpcId(session.getNextRpcId());
        node.setFromIp(NetUtils.getLocalHost());
        // node.setAddressIp(String.valueOf(in.getOtherArgs("host")));
        // String host = String.valueOf(in.getOtherArgs("host"));
        // String port = String.valueOf(in.getOtherArgs("port"));
        String db = "unknown";
        Connection conn = (Connection) in.getOtherArgs("this");
        String host = conn.getHost();
        String port = String.valueOf(conn.getPort());
        node.setAddressIp(host);
        if (conn instanceof BinaryClient) {
            db = String.valueOf(((BinaryClient) conn).getDB());
        }
        node.setServicePath(host + ":" + port + "?database=" + db);
        // node.setServiceName(command.name());
        // String[] params = (String[]) in.args[1];
        // node.setInParam(TraceUtils.toJson(params));
        node.setNodeType("Redis");
        return node;
    }

    @Override
    public EventType getEventType() {
        return EventType.jedis;
    }
}
