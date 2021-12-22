package com.tuling.teach.service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return "Hell" + name + runtimeMXBean.getName();
    }

    int clusterInvokerCount = 0;

    @Override
    public String clusterError(String error, Float
            errorRate) {
        clusterInvokerCount++;
        if ((clusterInvokerCount % 3) == 0) {
            throw new RuntimeException(error + "手动失败 clusterInvokerCount=" + clusterInvokerCount);
        }
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return "clusterHello:" + error + ":" + runtimeMXBean.getName();
    }


}