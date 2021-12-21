package com.test.cbt.server;/**
 * Created by Administrator on 2018/6/8.
 */

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/8
 **/
public class Bootstrap {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "dubbo-provider.xml");
        context.start();
        System.out.println("dubbo multicast 服务启动成功 ");
        System.in.read(); // press any key to exit
    }
}
