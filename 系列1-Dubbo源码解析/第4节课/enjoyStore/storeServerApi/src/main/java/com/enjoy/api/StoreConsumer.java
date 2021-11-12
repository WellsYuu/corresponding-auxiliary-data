package com.enjoy.api;

import com.alibaba.dubbo.config.*;
import com.enjoy.service.VipUserService;

import java.io.IOException;

public class StoreConsumer {
    public static void main(String[] args) throws IOException {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("StoreServerClientApi");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("172.17.0.2:2181");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20882);
        protocol.setThreads(100);

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        // 引用远程服务
        ReferenceConfig<VipUserService> reference = new ReferenceConfig<>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(VipUserService.class);

        // 和本地bean一样使用xxxService
        VipUserService vipUserService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
        String ret = vipUserService.getVipDetail("123");
        reference.destroy();
        System.out.println(ret);



    }

}
