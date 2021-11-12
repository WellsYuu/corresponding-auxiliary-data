package com.enjoy.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRmiServer {
    public static void main(String[] args) {
        testRmiClient();
    }

    public static void testRmiClient() {
        ApplicationContext ac=new ClassPathXmlApplicationContext("spring-server.xml");

        System.out.println("RMI服务端启动完成");
    }
}

