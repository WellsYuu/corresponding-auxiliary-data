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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Atomic02Example
{
    static AtomicReference<String> atomicInteger=new AtomicReference<String> ("A");
    //ABA问题
    public static void main(String[] args) throws InterruptedException {
        Thread t1= new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInteger.compareAndSet("A","B");
                atomicInteger.compareAndSet("B","A");

            }
        });

        Thread t2= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               boolean flag= atomicInteger.compareAndSet("A","B");
                System.out.println("是否可以修改成功:"+flag);
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

}
