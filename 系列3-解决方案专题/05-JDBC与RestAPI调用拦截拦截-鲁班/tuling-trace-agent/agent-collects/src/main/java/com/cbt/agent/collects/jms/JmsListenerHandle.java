package com.cbt.agent.collects.jms;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.common.util.ClassUtils;
import com.cbt.agent.common.util.NetUtils;
import com.cbt.agent.common.util.StringUtils;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceRequest;
import com.cbt.agent.trace.TraceSession;
import com.cbt.agent.collect.*;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

public class JmsListenerHandle implements CollectHandle {
    private static final String targetClass = "org.springframework.jms.listener.AbstractMessageListenerContainer";
    private static final String targetMethod = "invokeListener";
    private final Logger logger = LoggerFactory.getLogger(JmsListenerHandle.class);
    public static final boolean activeMQWsPresent = ClassUtils.isPresent("org.apache.activemq.ActiveMQConnectionFactory",
            JmsListenerHandle.class.getClassLoader());

    @Override
    public Event invokerBefore(Event event, InParams in) {
        Message message = (Message) in.args[1];
        TraceNode node = new TraceNode();
        TraceSession session;
        try {
            TraceRequest request = parseMessage(message);
            if (request != null) {
                session = TraceContext.getDefault().openTrace(request);
            } else {
                return null;
            }

        } catch (JMSException e) {
            logger.error("JMS collect Error", e);
            return null;
        }
        Event newEvent = new Event(getEventType(), true);
        node.setBeginTime(System.currentTimeMillis());
        node.setAddressIp(NetUtils.getLocalHost());
        node.setNodeType("mqListener");
        node.setTraceId(session.getTraceId());
        node.setRpcId(session.getNextRpcId());
        newEvent.setData(node);
        return newEvent;
    }

    @Override
    public void invokerAfter(Event event, OutResult out) {
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        if (session == null) {
            return;
        } else if (event == null || !event.isActive()) {
            TraceContext.getDefault().closeTrace(session);
            return;
        }
        TraceNode node = (TraceNode) event.getData();
        Message message = (Message) out.args[1];
        node.setEndTime(System.currentTimeMillis());
        if (out.error != null) {
            node.setResultState("fail");
            node.setErrorMessage(out.error.getMessage());
            node.setErrorStack(StringUtils.getErrorStackText(out.error));
        }
        node.setServicePath("Mq Path is unknown!!");
        try {
            if (activeMQWsPresent) {
                if (message instanceof ActiveMQTextMessage) {
                    String url = ((ActiveMQTextMessage) message).getConnection().getTransport().toString();
                    node.setServicePath(url);
                }
            }
            node.setServiceName(message.getJMSDestination().toString());
            if (message instanceof TextMessage) {
                node.setInParam(((TextMessage) message).getText());
            }
            node.setResultState("sucess");
        } catch (JMSException e) {
            if (out.error != null) { // 优先记录out.error 的异常
                node.setResultState("fail");
                node.setErrorMessage(out.error.getMessage());
                node.setErrorStack(StringUtils.getErrorStackText(out.error));
            } else {
                logger.error(e);
            }
        }
        session.addNode(node);
        TraceContext.getDefault().closeTrace(session);
    }

    private TraceRequest parseMessage(Message message) throws JMSException {
        TraceRequest request;
        // $traceId
        // $parentRpcId
        // $traceProperties

        if (message.propertyExists("$traceId")) {
            request = new TraceRequest();
            request.setTraceId(message.getStringProperty("$traceId"));
        } else {
            return null;
        }
        if (request != null && message.propertyExists("$parentRpcId")) {
            request.setParentRpcId(message.getStringProperty("$parentRpcId"));
        }
        if (request != null && message.propertyExists("$traceProperties")) {
            Properties properties = new Properties();
            ByteArrayInputStream input = new ByteArrayInputStream(message.getStringProperty("$traceProperties").getBytes());
            try {
                properties.load(input);
                request.setProperties(properties);
            } catch (IOException e) {
                logger.error("trace Properties loade error", e);
            }
        }

        return request;
    }

    @Override
    public EventType getEventType() {
        return EventType.jms_listener_spring;
    }
}
