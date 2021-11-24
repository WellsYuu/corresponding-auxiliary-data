package com.gupao.vip.mic.dubbo.jms;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SpringJmsSender {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext(
                        "classpath:META-INF/spring/service-jms.xml");

        JmsTemplate jmsTemplate=(JmsTemplate) context.getBean("jmsTemplate");

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message=session.createTextMessage();
                message.setText("Hello,mic");
                return message;
            }
        });
    }
}
