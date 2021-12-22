package com.tuling.teach.service.async;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class AsyncDemoServiceImpl implements AsyncDemoService {

    public static String getPId() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getName();
    }

    @Override
    public String sayHello1(String name) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sayHello1" + getPId() + "use 300ms";
    }

    @Override
    public String sayHello2(String name) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sayHello2" + getPId() + "use 500ms";
    }

    @Override
    public String notReturn(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return "notReturn:" + runtimeMXBean.getName();
    }
}