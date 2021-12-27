package com.tl.task;/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * 以往视频加小乔老师QQ：895900009
 * 悟空老师QQ：245553999
 */

import com.tl.util.OrderServer;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.CountDownLatch;

public class OrderTask implements Runnable {
    CountDownLatch latch;
    OrderServer orderServer;
    InterProcessMutex interProcessMutex;


    public OrderTask(CountDownLatch latch, OrderServer orderServer) {
        this.latch = latch;
        this.orderServer = orderServer;
    }

    public OrderTask(CountDownLatch latch, OrderServer orderServer,InterProcessMutex interProcessMutex) {
        this.latch = latch;
        this.orderServer = orderServer;
        this.interProcessMutex=interProcessMutex;
    }
    public void run() {
        try {
            //latch.await();
           // interProcessMutex.acquire();
            System.out.println(Thread.currentThread().getName()+"订单号:"+orderServer.getOrderNo());
           // interProcessMutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
