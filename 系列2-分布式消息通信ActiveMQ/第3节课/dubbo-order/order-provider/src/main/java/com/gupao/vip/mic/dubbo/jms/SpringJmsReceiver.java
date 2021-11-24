package com.gupao.vip.mic.dubbo.jms;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SpringJmsReceiver {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext(
                        "classpath:META-INF/spring/service-jms.xml");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*JmsTemplate jmsTemplate=(JmsTemplate) context.getBean("jmsTemplate");

        String msg=(String)jmsTemplate.receiveAndConvert();

        System.out.println(msg);*/
    }
}
