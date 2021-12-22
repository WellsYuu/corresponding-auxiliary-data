package com.tuling.teach.service.router;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class RouterDemoServiceImpl implements RouterDemoService {

    public static String getPId() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getName();
    }


    @Override
    public String sayHello(String name) {
        return getPId();
    }
}