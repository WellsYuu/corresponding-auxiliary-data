package com.tuling.teach.service.cluster;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Tommy on 2017/12/18.
 */
public class ClusterProtocol {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "cluster-provider.xml", ClusterProtocol.class);
        context.start();
        System.out.println("dubbo cluster Demo 服务启动成功 ");
        System.in.read(); // press any key to exit
    }

}
