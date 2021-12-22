package com.tl.executor.locks.semaphore;/*
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    static final Semaphore semaphor=new Semaphore(10);//允许10个线程获取许可.最大的并发数量

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();
        for (int i=0;i<10000;i++){

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    hander();
                }
            });
        }
        executorService.shutdown();
    }

    private static void hander() {
        boolean acquire=semaphor.tryAcquire();
        try {
            if(acquire){
                System.out.println("没有限流，执行正常业务");
                try {
                    Thread.sleep(new Random().nextInt(200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else
            {
                System.err.println("我被限流了");
            }
        } finally {
            semaphor.release();
        }
    }

}
