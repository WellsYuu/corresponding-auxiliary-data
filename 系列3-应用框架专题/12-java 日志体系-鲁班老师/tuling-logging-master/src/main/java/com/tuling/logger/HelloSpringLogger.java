package com.tuling.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tommy on 2017/11/27.
 */
public class HelloSpringLogger {
    private static Logger logger = LoggerFactory.getLogger(HelloSlf4j.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context
                = new ClassPathXmlApplicationContext("spring.xml");
        context.start();
        context.getBean(HelloSpringLogger.class);

        logger.error("slf4j- error - abcd");
        logger.info("slf4j- info -abcd");
    }
}
