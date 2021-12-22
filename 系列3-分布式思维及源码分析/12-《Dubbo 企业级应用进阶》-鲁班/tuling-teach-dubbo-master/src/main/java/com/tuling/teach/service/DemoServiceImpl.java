package com.tuling.teach.service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return "Hell" + name+runtimeMXBean.getName();

    }
}