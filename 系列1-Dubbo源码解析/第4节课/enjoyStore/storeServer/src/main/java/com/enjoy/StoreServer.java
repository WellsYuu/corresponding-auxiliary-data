package com.enjoy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StoreServer {
    public static void main(String[] args) throws IOException {
        /**
         * dubbo.xml
         * dubbo_annotation.xml
         */
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:dubbo.xml");
        context.start();

        System.out.println("-----dubbo开启-----");

        // 保证服务一直开着
        synchronized (StoreServer.class) {
            try {
                StoreServer.class.wait();
            } catch (Throwable e) {
            }
        }
    }
}
