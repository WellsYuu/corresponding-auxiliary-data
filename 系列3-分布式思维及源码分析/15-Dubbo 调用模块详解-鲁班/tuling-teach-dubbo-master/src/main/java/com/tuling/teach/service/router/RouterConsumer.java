package com.tuling.teach.service.router;

import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RouterConsumer {
    static RouterDemoService demoService;

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "router-consumer.xml", RouterConsumer.class);
        context.start();
        demoService = (RouterDemoService) context.getBean("routerDemoService"); // obtain proxy object for remote invocation

        while (true) {
            byte[] b = new byte[1024];
            int szie = System.in.read(b);
            String cmd = new String(b, 0, szie).trim();
            try {

                if (cmd.equals("send")) {
                    System.out.println(demoService.sayHello("hanmeimei"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}