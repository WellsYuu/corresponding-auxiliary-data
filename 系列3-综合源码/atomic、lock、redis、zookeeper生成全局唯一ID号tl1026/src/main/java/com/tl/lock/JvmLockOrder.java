package com.tl.lock;/*
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

import com.tl.task.OrderTask;
import com.tl.util.OrderLockServiceImpl;
import com.tl.util.OrderServer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JvmLockOrder {

    public static void main(String[] args) {
        ExecutorService executorServer=   Executors.newCachedThreadPool();
        final CountDownLatch latch=new CountDownLatch(1);
        for (int i=0;i<10;i++){
            OrderServer orderServer=new OrderLockServiceImpl();
            executorServer.submit(new OrderTask(latch, orderServer));
        }
        latch.countDown();;
        executorServer.shutdown();
    }
}
