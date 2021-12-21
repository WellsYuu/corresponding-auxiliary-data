package com.cbt.agent.collects.jms;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.common.util.ClassUtils;
import com.cbt.agent.common.util.NetUtils;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceRequest;
import com.cbt.agent.trace.TraceSession;
import com.cbt.agent.collect.*;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class JmsTemplateHandle implements CollectHandle {
    public static final String targetClass = "org.springframework.jms.core.JmsTemplate";
    public static final String targetMethod = "doSend";
    private final Logger logger = LoggerFactory.getLogger(JmsTemplateHandle.class);
    public static final boolean activeMQWsPresent = ClassUtils.isPresent("org.apache.activemq.command.ActiveMQTextMessage",
            JmsListenerHandle.class.getClassLoader());
    @Override
    public Event invokerBefore(Event event, InParams in) {
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        if (session == null) {
            return null;
        }
        Event newEvent = new Event(getEventType(), true);
        TraceNode data = new TraceNode();
        data.setTraceId(session.getTraceId());
        data.setRpcId(session.getNextRpcId());
        data.setBeginTime(System.currentTimeMillis());
        MessageProducer producer = (MessageProducer) in.args[0];
        Message message = (Message) in.args[1];
        data.setFromIp(NetUtils.getLocalHost());
        data.setNodeType("MQ");
        try {
            String destination = producer.getDestination().toString();
            data.setServiceName(destination); // 目的地 即topic 主题
            data.setServicePath("mq path is unknown!");
            if (activeMQWsPresent) {
                if (message instanceof ActiveMQTextMessage) {
                    String url = ((ActiveMQTextMessage) message).getConnection().getTransport().toString();
                    data.setServicePath(url);
                }
            }
            if (message instanceof TextMessage) {
                // 输出内容
                data.setInParam(((TextMessage) message).getText());
            }
            // 设置向下游传递Trace Request 属性
            transmitTraceRequest(message, data, session);
        } catch (JMSException e) {
            data.setErrorMessage(e.getMessage());
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(outStream));
            data.setErrorStack(outStream.toString());
            // 提前关闭
            newEvent.close();
            session.addNode(data);
        }

        newEvent.setData(data);
        return newEvent;
    }

    @Override
    public void invokerAfter(Event event, OutResult out) {
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        if (session == null)
            return;
        if (event == null || !event.isActive())
            return;
        TraceNode data = (TraceNode) event.getData();
        data.setEndTime(System.currentTimeMillis());
        data.setResultState("sucess");
        if (out.error != null) {
            data.setErrorMessage(out.error.getMessage());
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            out.error.printStackTrace(new PrintStream(outStream));
            data.setErrorStack(outStream.toString());
            data.setResultState("fail");
        }
        data.setEndTime(System.currentTimeMillis());
        session.addNode(data);
    }

    @Override
    public EventType getEventType() {
        return EventType.JMS_SEND_TEMPLATE;
    }

    /**
     * 
     * 向下游传递Trace属性值. <br/>
     * 
     * @author zengguangwei@cbt.com
     * @date: 2016年7月27日 上午11:08:04
     * @version 1.0
     * 
     * @param msg
     * @param data
     * @param session
     * @throws JMSException
     */
    private void transmitTraceRequest(Message msg, TraceNode data, TraceSession session) throws JMSException {
        try {
            TraceRequest request = session.getTraceRequest();
            msg.setStringProperty("$traceId", request.getTraceId());
            msg.setStringProperty("$parentRpcId", data.getRpcId());
            if (request.getProperties() != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                request.getProperties().store(outStream, "");
                msg.setStringProperty("$traceProperties", outStream.toString());
            }
        } catch (IOException e) {
            logger.error("Trace JMS Property store Error", e);
        }
    }

}
