package com.cbt.agent.collects.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.RpcContext;
import com.cbt.agent.collect.*;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceRequest;
import com.cbt.agent.trace.TraceSession;
import com.cbt.agent.collect.*;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * dubbo 提供者监控
 * 
 * @since 0.1.0
 */
public class DubboProviderMonitorHandle implements CollectHandle {
	private static final String TARGET_CLASS = "com.alibaba.dubbo.rpc.filter.EchoFilter";
	private static final String DISPATCH_NAME = "invoke";

	@Override
	public Event invokerBefore(Event event, InParams in) {
		Assert.isTrue(TARGET_CLASS.equals(in.className));
		Assert.isTrue(DISPATCH_NAME.equals(in.methodName));
		Event newEvent=new Event(getEventType(),true);

		Invocation invocation = (Invocation) in.args[1];
		String traceId = invocation.getAttachment(TraceContext.TID);
		String parentId = invocation.getAttachment(TraceContext.PID);
		String properties = invocation.getAttachment(TraceContext.PROPERTIES);
		if (traceId != null) {
			TraceRequest request = new TraceRequest();
			request.setTraceId(traceId);
			request.setParentRpcId(parentId == null ? "0" : parentId);
			if (properties != null) {
				request.setProperties(toProperties(properties));
			}
			// 开启监控
			TraceSession session = TraceContext.getDefault().openTrace(request);
			RpcContext context = RpcContext.getContext();
			String servicePath = invocation.getAttachments().get("interface");
			String methodName = invocation.getMethodName();
			String addressIp = context.getLocalAddressString();
			String fromIp = context.getRemoteAddressString();
			TraceNode node = new TraceNode();
			node.setNodeType("dubbo-provider");
			node.setTraceId(traceId);
			node.setRpcId(parentId);
			node.setBeginTime(System.currentTimeMillis());
			node.setAddressIp(addressIp);
			node.setFromIp(fromIp);
			node.setServicePath(servicePath);
			node.setServiceName(methodName);
			newEvent.setData(node);
		}else{
			newEvent.close();
		}
		return newEvent;
	}

	@Override
	public EventType getEventType() {
		return EventType.dubbo_provider;
	}

	private Properties toProperties(String properties) {
		Properties propertie = null;
		try {
			propertie = new Properties();
			propertie.load(new ByteArrayInputStream(properties.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertie;
	}

	@Override
	public void invokerAfter(Event event, OutResult out) {
		Assert.isTrue(TARGET_CLASS.equals(out.className));
		Assert.isTrue(DISPATCH_NAME.equals(out.methodName));
		TraceSession session = TraceContext.getDefault().getCurrentSession();
		if(session==null){
		    return;
		}else if (event == null||!event.isActive()) {
			return;
		}
		if (session != null) {
			TraceNode node= (TraceNode) event.getData();
			node.setEndTime(System.currentTimeMillis());
			session.addNode(node);
			TraceContext.getDefault().closeTrace(session);
		}
	}
}
