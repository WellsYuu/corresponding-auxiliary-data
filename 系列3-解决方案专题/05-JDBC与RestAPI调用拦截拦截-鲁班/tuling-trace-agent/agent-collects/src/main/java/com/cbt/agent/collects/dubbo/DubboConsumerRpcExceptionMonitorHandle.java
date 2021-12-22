package com.cbt.agent.collects.dubbo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.springframework.util.Assert;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.rpc.Invoker;
import com.cbt.agent.collect.CollectHandle;
import com.cbt.agent.collect.Event;
import com.cbt.agent.collect.EventType;
import com.cbt.agent.collect.InParams;
import com.cbt.agent.collect.OutResult;
import com.cbt.agent.common.util.JsonUtils;
import com.cbt.agent.common.util.NetUtils;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceSession;

/**
 * dubbo 消费者监控 .
 * TODO 待改进,是否只需监听一个对象即可,不需要监听 FutureFilter?
 *
 * @since 0.1.0
 */
public class DubboConsumerRpcExceptionMonitorHandle implements CollectHandle {
    public static final String TARGET_CLASS = "com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler";
    public static final String DISPATCH_NAME = "invoke";

    @Override
    public Event invokerBefore(Event event, InParams in) {
        Assert.isTrue(TARGET_CLASS.equals(in.className));
        Assert.isTrue(DISPATCH_NAME.equals(in.methodName));
        if (TraceContext.getDefault().getCurrentSession() == null)
            return null;

        Event newevent = new Event(getEventType(), true);
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        String rpcid = session.getNextRpcId();
        TraceNode node = new TraceNode();

        Invoker invoker = (Invoker) in.getOthers().get("invoker");
        String servicePath = invoker.getInterface().getName();
        String groupName = invoker.getUrl().getParameter(Constants.GROUP_KEY);
        String dubboVersion = invoker.getUrl().getParameter(Constants.VERSION_KEY);
        Method method = (Method) in.args[1];
        String methodName = method.getName();
        Object[] param = (Object[]) in.args[2];
        node.setNodeType("dubbo");
        node.setTraceId(session.getTraceId());
        node.setRpcId(rpcid);
        node.setServicePath(servicePath);
        if (groupName != null && dubboVersion != null) {
            node.setServicePath(servicePath + "(" + groupName + "/" + dubboVersion + ")");
        } else {
            node.setServicePath(servicePath);
        }
        node.setServiceName(methodName);
        node.setBeginTime(System.currentTimeMillis());
        node.setAddressIp(NetUtils.getLocalHost());
        node.setInParam(JsonUtils.toJson(param, getClass().getClassLoader()));
        newevent.setData(node);

        return newevent;
    }

    @Override
    public void invokerAfter(Event event, OutResult out) {
        Assert.isTrue(TARGET_CLASS.equals(out.className));
        Assert.isTrue(DISPATCH_NAME.equals(out.methodName));
        if (TraceContext.getDefault().getCurrentSession() == null) {
            return;
        } else if (event == null || !event.isActive()) {
            return;
        }
        // 只捕捉异常信息
        TraceNode node = (TraceNode) event.getData();
        Throwable throwable = out.error;
        if (throwable != null ) {
            node.setEndTime(System.currentTimeMillis());
            node.setErrorMessage(throwable.getMessage());
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outStream);
            throwable.printStackTrace(printStream);
            node.setErrorStack(outStream.toString());
            node.setResultState("fail");
        }
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        session.addNode(node);
    }

    @Override
    public EventType getEventType() {
        return EventType.dubbo_consumer;
    }
}
