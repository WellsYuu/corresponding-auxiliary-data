package com.tuling.teach.service.protocol;

import com.tuling.teach.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Tommy on 2017/12/18.
 */
public class DubboProtocolConsumer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "dubbo-consumer.xml",DubboProtocolConsumer.class);
        context.start();
        Object userServic = context.getBean("userService");
        DemoService demoService = (DemoService) context.getBean("demoService"); // obtain proxy object for remote invocation
        String hello = demoService.sayHello("world"); // execute remote invocation
        System.out.println(hello); // show the result

        while (true) {
            byte[] b = new byte[1024];
            int szie = System.in.read(b);
            if (new String(b, 0, szie).trim().equals("send")) {
                try {
                    System.out.println(demoService.sayHello("hanmeimei"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
