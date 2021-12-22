package com.tuling.teach.service.async;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tuling.teach.service.cluster.ClusterDemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncConsumer {
    static AsyncDemoService demoService;

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "async-consumer.xml", AsyncConsumer.class);
        context.start();
        demoService = (AsyncDemoService) context.getBean("asyncDemoService"); // obtain proxy object for remote invocation

        while (true) {
            byte[] b = new byte[1024];
            int szie = System.in.read(b);
            String cmd = new String(b, 0, szie).trim();
            try {

                if (cmd.equals("sync")) {
                    doSync();
                } else if (cmd.equals("async")) {
                    doAsync();
                } else if (cmd.equals("notReturn")) {
                    doNotReturn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void doNotReturn() {
        long begin = System.currentTimeMillis();
        String r1 = demoService.notReturn("hahaha");
        System.out.println("无返回结果调用时:" + (System.currentTimeMillis() - begin) + "结果:" + r1);

    }

    // 异步发起调用
    private static void doAsync() {
        long begin = System.currentTimeMillis();
        demoService.sayHello1("han");
        Future<Object> future1 = RpcContext.getContext().getFuture();
        demoService.sayHello2("han2");
        Future<Object> future2 = RpcContext.getContext().getFuture();
        Object r1 = null, r2 = null;
        try {
            r1 = future1.get();
            r2 = future2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("异步调用时:" + (System.currentTimeMillis() - begin) + "结果:" + r1 + "    " + r2);
    }

    // 同步发起调用
    private static void doSync() {
        long begin = System.currentTimeMillis();
        String r1 = demoService.sayHello1("han");
        String r2 = demoService.sayHello2("han2");
        System.out.println("同步调用时:" + (System.currentTimeMillis() - begin) + "结果:" + r1 + "    " + r2);
    }
}