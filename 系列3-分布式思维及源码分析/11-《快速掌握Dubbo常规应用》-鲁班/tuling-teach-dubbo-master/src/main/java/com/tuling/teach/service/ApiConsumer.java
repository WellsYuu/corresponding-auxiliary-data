package com.tuling.teach.service;

import com.alibaba.dubbo.config.*;

/**
 * Created by Tommy on 2017/12/14.
 */
public class ApiConsumer {
    private final RegistryConfig registryConfig;
    private final ReferenceConfig referenceConfig;
    ApplicationConfig applicationConfig;

    public ApiConsumer() {
        applicationConfig = new ApplicationConfig();
        applicationConfig.setName("tuling-demo-provider");
        // 注册中心
        registryConfig = new RegistryConfig();
        registryConfig.setProtocol("multicast");
        //组播受网络结构限制，只适合小规模应用或开发阶段使用。组播地址段: 224.0.0.0 - 239.255.255.255
        registryConfig.setAddress("224.5.6.7:1234");

        referenceConfig = new ReferenceConfig();
        referenceConfig.setInterface("com.tuling.teach.service.DemoService");
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setApplication(applicationConfig);
    }

    public Object getRemoteService() {
        return referenceConfig.get();
    }

    public static void main(String[] args) {
        ApiConsumer apiConsumer = new ApiConsumer();
        DemoService service = (DemoService) apiConsumer.getRemoteService();
        String result = service.sayHello("hanmeimei");
        System.out.println(result);
    }
}
