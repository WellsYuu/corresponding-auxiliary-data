package com.tuling.teach.service.generalized;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tommy on 2017/12/24.
 */
public class GeneralizedConsumer {
    public static void main(String[] args) throws IOException {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("demo-provider");
        // 注册中心
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("192.168.0.147:2181");
        // 引用远程服务
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        // 弱类型接口名
        reference.setInterface("com.tuling.teach.service.DemoService");
        // 声明为泛化接口
        reference.setGeneric(true);
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        while (true) {
            byte[] b = new byte[1024];
            int szie = System.in.read(b);
            String cmd = new String(b, 0, szie).trim();
            try {

                if (cmd.equals("send")) {
                    Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"world"});
                    System.out.println(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


       /* // 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
        Map<String, Object> person = new HashMap<String, Object>();
        person.put("name", "xxx");
        person.put("password", "yyy");
        // 如果返回POJO将自动转成Map
        result = genericService.$invoke("findPerson", new String[]
                {"com.xxx.Person"}, new Object[]{person});*/
    }
}
