package com.tuling.teach.service.router;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Tommy on 2017/12/18.
 */
public class RouterProtocol {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "router-provider.xml", RouterProtocol.class);
        context.start();
        System.out.println("dubbo async Demo 服务启动成功 ");
        System.in.read(); // press any key to exit
    }

}
