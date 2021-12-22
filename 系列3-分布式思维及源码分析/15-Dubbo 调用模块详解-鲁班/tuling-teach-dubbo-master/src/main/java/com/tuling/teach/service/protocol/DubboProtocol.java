package com.tuling.teach.service.protocol;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Tommy on 2017/12/18.
 */
public class DubboProtocol {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "dubbo-provider.xml", DubboProtocol.class);
        context.start();
        System.out.println("dubbo 服务启动成功 ");
        System.in.read(); // press any key to exit
    }

}
