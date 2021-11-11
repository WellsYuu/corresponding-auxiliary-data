package com.enjoy.rmi;

import com.enjoy.service.VipUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRmiClient {
    public static void main(String[] args) {
        testRmiClient();
    }

    public static void testRmiClient() {
        ApplicationContext ac=new ClassPathXmlApplicationContext("spring-client.xml");
        //获取封装的远程服务对象
        VipUserService vipUserService=(VipUserService) ac.getBean("remoteService");

        String result = vipUserService.getVipDetail("test");

        System.out.println(result);
    }

}

