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
import com.tl.util.OrderNoLockServiceImpl;
import com.tl.util.OrderServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorLockOrder {
 final  static CuratorFramework client= CuratorFrameworkFactory.builder().connectString("123.56.21.219:2181,123.56.21.219:2182,123.56.21.219:2183").retryPolicy(new ExponentialBackoffRetry(100,1)).build();
    public static void main(String[] args) {
        client.start();
        ExecutorService executorServer=   Executors.newCachedThreadPool();
        final CountDownLatch latch=new CountDownLatch(1);
        final InterProcessMutex lock=new InterProcessMutex(client,"/tl1026");
        OrderServer orderServer=new OrderNoLockServiceImpl();
        for (int i=0;i<10;i++){
            executorServer.submit(new OrderTask(latch, orderServer,lock));
        }
        latch.countDown();;
        executorServer.shutdown();
    }
}
