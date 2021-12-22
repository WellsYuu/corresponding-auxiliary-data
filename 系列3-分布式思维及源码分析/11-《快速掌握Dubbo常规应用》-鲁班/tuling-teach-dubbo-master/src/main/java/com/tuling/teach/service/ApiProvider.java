package com.tuling.teach.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/12/14.
 */
public class ApiProvider {
    private final RegistryConfig registryConfig;
    protected final ProtocolConfig protocolConfig;
    private List services = new ArrayList();
    DemoServiceImpl demoService;
    ApplicationConfig applicationConfig;

    public ApiProvider() {
        demoService = new DemoServiceImpl();
        applicationConfig = new ApplicationConfig();
        applicationConfig.setName("tuling-demo-provider");
        registryConfig = new RegistryConfig();
        registryConfig.setProtocol("multicast");
        //组播受网络结构限制，只适合小规模应用或开发阶段使用。组播地址段: 224.0.0.0 - 239.255.255.255
        registryConfig.setAddress("224.5.6.7:1234");

        protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);


    }

    public void exportService(Class service) {
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setRef(demoService);
        serviceConfig.setInterface("com.tuling.teach.service.DemoService");
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.export();
        System.out.println(String.format("%s服务启动成功 端口20880", service.getSimpleName()));
        services.add(serviceConfig);
    }

    public static void main(String[] args) throws IOException {
        ApiProvider provider = new ApiProvider();
        provider.exportService(DemoService.class);
        System.in.read();
    }

}
