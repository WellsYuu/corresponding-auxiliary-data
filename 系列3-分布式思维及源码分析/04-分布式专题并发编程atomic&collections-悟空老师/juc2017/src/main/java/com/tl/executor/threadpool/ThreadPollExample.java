package com.tl.executor.threadpool;/*
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
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPollExample implements Runnable {
    AtomicInteger atomicInteger=new AtomicInteger();
    @Override
    public void run() {
        int task=atomicInteger.incrementAndGet();
        System.out.println("taksId:"+task);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecuto=   new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
               // new ArrayBlockingQueue<Runnable>()){
                new ArrayBlockingQueue<Runnable>(1)
      ){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("线程执行之前");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("线程执行之后");
            }
        };

        threadPoolExecuto.setRejectedExecutionHandler( new RejectedExecutionHandler(){
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("我是被拒绝");
            }
        });
        ThreadPollExample  threadPollExample=new ThreadPollExample();
        new Thread(new MonitorThreadPoolUtil(threadPoolExecuto,1)).start();
           for(int i=0;i<20;i++){
               threadPoolExecuto.submit(threadPollExample);

           }
        threadPoolExecuto.shutdown();

    }
}
