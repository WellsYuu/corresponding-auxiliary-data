package com.tl.executor.locks.tools;/*
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
 * JAVA学习交流群号：656951213
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args){
        final CountDownLatch countDownLatch=new CountDownLatch(2);
       new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("悟空-->老师在等待");
                try {
                    countDownLatch.await(); //已结给上锁了
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我们三个一起去大保健");
            }
        }).start();

       new Thread(new Runnable() {
           @Override
           public void run() {
               System.out.println("鲁班-->老师在Vip课");
               try {
                    Thread.sleep(3000);
                    countDownLatch.countDown();
                   System.out.println("鲁班-->老师Vip课讲完了");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

           }
       }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("张飞-->老师在看电视");
                try {
                    Thread.sleep(5000);
                    countDownLatch.countDown();
                    System.out.println("张飞-->老师看完电视了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }


}
