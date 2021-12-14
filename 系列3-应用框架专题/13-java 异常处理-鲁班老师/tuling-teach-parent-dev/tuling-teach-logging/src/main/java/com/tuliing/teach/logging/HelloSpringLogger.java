package com.tuliing.teach.logging;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tommy on 2017/10/29.
 */
public class HelloSpringLogger {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext xmlApplicationContext =
                new ClassPathXmlApplicationContext("spring.xml");
        xmlApplicationContext.start();
        xmlApplicationContext.getBean(HelloSpringLogger.class);
    }
}
