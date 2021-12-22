package com.tuling.teach.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "config/spring-dubbo-consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // obtain proxy object for remote invocation
       
        UserService userService = (UserService) context.getBean("userService");
        
        while (true) {
            byte[] b = new byte[1024];
            int szie = System.in.read(b);
            String cmd=new String(b, 0, szie).trim();
            if (cmd.startsWith("send")) {
                System.out.println(demoService.sayHello("hanmeimei"));
            }else if (cmd.startsWith("user")) {
                System.out.println(userService.searchUser(cmd,new UserInfo(cmd,"男",1)));
            }
        }
    }
}