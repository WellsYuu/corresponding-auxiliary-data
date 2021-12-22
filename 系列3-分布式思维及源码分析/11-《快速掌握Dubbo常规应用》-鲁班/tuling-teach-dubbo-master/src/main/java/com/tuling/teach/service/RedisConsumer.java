package com.tuling.teach.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedisConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "config/spring-dubbo-redis-consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // obtain proxy object for remote invocation
        String hello = demoService.sayHello("world"); // execute remote invocation
        System.out.println(hello); // show the result

       /* while (true) {
            byte[] b = new byte[1024];
            int szie = System.in.read(b);
            if (new String(b, 0, szie).trim().equals("send")) {
                demoService.sayHello("hanmeimei");
            }
        }*/

    }
}