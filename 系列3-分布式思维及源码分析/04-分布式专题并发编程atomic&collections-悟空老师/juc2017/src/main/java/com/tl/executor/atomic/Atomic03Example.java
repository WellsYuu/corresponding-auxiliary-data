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

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Atomic03Example
{
    static AtomicStampedReference<String> atomicInteger=new AtomicStampedReference<String> ("A",0);
    /***ABA问题**/
    public static void main(String[] args) throws InterruptedException {
        Thread t1= new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInteger.compareAndSet("A","B",atomicInteger.getStamp(),atomicInteger.getStamp()+1);
                atomicInteger.compareAndSet("B","A",atomicInteger.getStamp(),atomicInteger.getStamp()+1);

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
               boolean flag= atomicInteger.compareAndSet("A","B",0,atomicInteger.getStamp()+1);
                System.out.println("是否可以修改成功:"+flag);
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

}
