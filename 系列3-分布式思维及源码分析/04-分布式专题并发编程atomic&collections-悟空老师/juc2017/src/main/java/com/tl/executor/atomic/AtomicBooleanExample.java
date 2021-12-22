package com.tl.executor.atomic;/*
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

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanExample {
    int x = 0;
   // volatile   boolean v = false;

    AtomicBoolean v=new AtomicBoolean(false);
    public void writer() {
        x = 42;
       // v = true;
        v.compareAndSet(false,true);
    }

    public void reader() {
        while (!v.get()) {
           //死循环
        }
        System.out.println("悟空是个猴子");

    }

    public static void main(String[] args) throws InterruptedException {
       final AtomicBooleanExample volatileExample=new AtomicBooleanExample();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                volatileExample.reader();
            }
        });
        thread.start();
        Thread.sleep(1000);
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                volatileExample.writer();
                System.out.println(volatileExample.v);
            }
        });
        thread2.start();



    }

}