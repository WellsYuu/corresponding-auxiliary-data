package com.enjoy.rmi;

import com.enjoy.service.VipUserService;
import com.enjoy.service.impl.VipUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class RimConfigure {

    private int port = 1919;

    @Bean
    public RmiServiceExporter serviceExporter() {
        RmiServiceExporter rse = new RmiServiceExporter();

        //路径名字---- VipUserService全路径名
        rse.setServiceName(VipUserService.class.getName());

        //端口号是1919
        rse.setRegistryPort(port);

        //暴露给外部的访问接口VipUserService
        rse.setServiceInterface(VipUserService.class);

        //rmi实际使用的对象
        rse.setService(new VipUserServiceImpl());

        return rse;
    }

}

