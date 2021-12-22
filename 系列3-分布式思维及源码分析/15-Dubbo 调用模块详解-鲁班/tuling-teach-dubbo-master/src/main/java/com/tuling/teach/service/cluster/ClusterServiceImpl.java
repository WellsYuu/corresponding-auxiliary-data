package com.tuling.teach.service.cluster;

import com.alibaba.dubbo.rpc.RpcException;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class ClusterServiceImpl implements ClusterDemoService {
    public String sayHello(String name) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return "Hell" + name + runtimeMXBean.getName();
    }

    public static String getPId() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getName();
    }

    volatile int invokerCount = 0;

    /**
     * 失败自动切换，当出现失败，重试其它服务器
     * 1。通常用于读操作，但重试会带来更长延迟。可通过 retries="2" 来设置重试次数(不含第一次)。
     *
     * @param error
     * @return
     */
    @Override
    public String failover(String error) {
        invokerCount++;
        if (invokerCount % 3 == 0) {
            throwTimeout(1500);        // 人为造成超时
//            throw new RpcException(1,String.format("%s, %s,invokerCount=%s", getPId(), error, invokerCount));
        }
        return String.format("failover %s, invokerCount=%s", getPId(), invokerCount);
    }
    public void throwTimeout(long time){
        try {
            //
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 快速失败，只发起一次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录
     *
     * @param error
     * @return
     */
    @Override
    public String failfast(String error) {
        invokerCount++;
        if (invokerCount % 3 == 0) {
            throwTimeout(1500);        // 人为造成超时
//            throw new RuntimeException(String.format("%s, %s,invokerCount=%s", getPId(), error, invokerCount));
        }
        return String.format("failfast %s, invokerCount=%s", getPId(), invokerCount);
    }

    /**
     * 失败安全，出现异常时，直接忽略。通常用于写入审计日志等操作。
     *
     * @param error
     * @return
     */
    @Override
    public String failsafe(String error) {
        invokerCount++;
        if (invokerCount % 3 == 0) {
            throwTimeout(1500);        // 人为造成超时
//            throw new RuntimeException(String.format("%s, %s,invokerCount=%s", getPId(), error, invokerCount));
        }
        return String.format("failsafe %s, invokerCount=%s", getPId(), invokerCount);
    }

    /**
     * 失败自动恢复，后台记录失败请求，定时重发。通常用于消息通知操作。
     *
     * @param error
     * @return
     */
    @Override
    public String failback(String error) {
        invokerCount++;
        if (invokerCount % 3 == 0) {
            throwTimeout(1500);        // 人为造成超时
//            throw new RuntimeException(String.format("%s, %s,invokerCount=%s", getPId(), error, invokerCount));
        }
        return String.format("failback  %s, invokerCount=%s", getPId(), invokerCount);
    }

    /**
     * 并行调用多个服务器，只要一个成功即返回。
     * 通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过 forks="2" 来设置最大并行数
     *
     * @param info
     * @return
     */
    @Override
    public String forking(String info) {

        System.out.println(getPId()+"我被并行 调用了");
        /*invokerCount++;
        if (invokerCount % 3 == 0) {
            throw new RuntimeException(String.format("%s, %s,invokerCount=%s", getPId(), info, invokerCount));
        }*/
        return String.format("forking  %s, invokerCount=%s", getPId(), invokerCount);
    }

    /**
     * 广播调用所有提供者，逐个调用，
     * 任意一台报错则报错 2。通常用于通知所有提供者更新缓存或日志等本地资源信息。
     *
     * @param info
     * @return
     */
    @Override
    public String broadcast(String info) {
        invokerCount++;
        System.out.println(getPId()+"我被广播 调用了");
        /*if (invokerCount % 3 == 0) {
            throw new RuntimeException(String.format("%s, %s,invokerCount=%s", getPId(), info, invokerCount));
        }*/
        return String.format("broadcast  %s, invokerCount=%s", getPId(), invokerCount);
    }


}