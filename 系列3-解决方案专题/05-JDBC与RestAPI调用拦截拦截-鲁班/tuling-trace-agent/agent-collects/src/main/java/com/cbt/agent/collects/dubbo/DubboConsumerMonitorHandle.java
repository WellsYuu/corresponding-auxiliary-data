package com.cbt.agent.collects.dubbo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.springframework.util.Assert;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.cbt.agent.collect.CollectHandle;
import com.cbt.agent.collect.Event;
import com.cbt.agent.collect.EventType;
import com.cbt.agent.collect.InParams;
import com.cbt.agent.collect.OutResult;
import com.cbt.agent.common.util.JsonUtils;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceSession;

/**
 * dubbo 消费者监控
 *
 * @since 0.1.0
 */
public class DubboConsumerMonitorHandle implements CollectHandle {
    public static final String TARGET_CLASS = "com.alibaba.dubbo.rpc.protocol.dubbo.filter.FutureFilter";
    public static final String DISPATCH_NAME = "invoke";

    @Override
    public Event invokerBefore(Event event, InParams in) {
        Assert.isTrue(TARGET_CLASS.equals(in.className));
        Assert.isTrue(DISPATCH_NAME.equals(in.methodName));
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        if (session == null) {
            return null;
        } else if (event == null || !event.isActive()) {
            return null;
        }
        Invocation invocation = (Invocation) in.args[1];
        RpcInvocation rpcInvocation = (RpcInvocation) invocation;
        Properties properties = session.getTraceRequest().getProperties();
        TraceNode node = (TraceNode) event.getData();
        // 设置需要向下游传递的参数
        setAttachment(node.getRpcId(), node.getTraceId(), properties, rpcInvocation);

        return null;
    }


    @Override
    public void invokerAfter(Event event, OutResult out) {
        Assert.isTrue(TARGET_CLASS.equals(out.className));
        Assert.isTrue(DISPATCH_NAME.equals(out.methodName));
        TraceSession session = TraceContext.getDefault().getCurrentSession();

        if (session == null) {
            return;
        } else if (event == null || !event.isActive()) {
            return;
        }
        TraceNode node = (TraceNode) event.getData();
        Throwable e = null;
        Object value = null;
        if (out.error == null) {
            Result result = (Result) out.result;
            e = result.getException();
            value = result.getValue();
        } else {
            e = out.error;
        }
        if (e != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outStream);
            e.printStackTrace(printStream);
            node.setErrorStack(outStream.toString());
            node.setErrorMessage(e.getMessage());
        }
        node.setResultState(e == null ? "succeed" : "fail");
        node.setEndTime(System.currentTimeMillis());
        if (session.isDubug()) {
            String jsonOutParam = JsonUtils.toJson(value, getClass().getClassLoader());
            node.setOutParam(jsonOutParam);
            node.setResultSize(jsonOutParam == null ? "0" : String.valueOf(jsonOutParam.length()));
        }
        // 统一在 InvokerHandle当中添加
        /*
         * session.addNode(node); event.close();
         */
    }

    private String convertProperties(Properties properties) {
        if (properties == null) return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            properties.store(stream, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

    /**
     * 设置下游传递参数
     *
     * @param pId        请求下个节点ID
     * @param tId        调用链ID
     * @param invocation
     */
    private void setAttachment(String pId, String tId, Properties properties, RpcInvocation invocation) {
        invocation.setAttachment(TraceContext.PID, pId);
        invocation.setAttachment(TraceContext.TID, tId);
        if (properties != null) {
            invocation.setAttachment(TraceContext.PROPERTIES, convertProperties(properties));
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.dubbo_consumer;
    }

}
