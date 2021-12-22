package com.tuling.teach.service.generalized;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Tommy on 2017/12/18.
 */
public class GeneralizedProtocol {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "generalized-provider.xml", GeneralizedProtocol.class);
        context.start();
        System.out.println("dubbo  Demo 服务启动成功 ");
        doExportGenericService();
        System.out.println("dubbo 泛化服务启动成功 ");

        System.in.read(); // press any key to exit
    }

    public static void doExportGenericService() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("demo-provider");
        // 注册中心
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("192.168.0.147:2181");

        ProtocolConfig protocol=new ProtocolConfig();
        protocol.setPort(-1);
        protocol.setName("dubbo");

        GenericService demoService = new MyGenericService();
        ServiceConfig<GenericService> service = new ServiceConfig<GenericService>();
        // 弱类型接口名
        service.setInterface("com.tuling.teach.service.DemoService");
        // 指向一个通用服务实现
        service.setRef(demoService);
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig);
        service.setProtocol(protocol);
        // 暴露及注册服务
        service.export();
    }
}
