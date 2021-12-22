package com.tl.threadpool;/*
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorsService {

    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(1);

        //等同
        ScheduledExecutorService scheduledExecutorService2= new ScheduledThreadPoolExecutor(1);

        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("悟空是只猴子1");
            }
        },0,5, TimeUnit.SECONDS);


        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("悟空是只猴子2");
                    throw  new RuntimeException();
                } catch (RuntimeException e) {
                    //e.printStackTrace();
                }

            }
        },0,5, TimeUnit.SECONDS);
    }
}
