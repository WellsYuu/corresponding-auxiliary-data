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
 *
 * 还没开始
 */

import java.util.concurrent.atomic.AtomicInteger;

public class Atomic01Example implements  Runnable{
   //static int i=0;
  static AtomicInteger i=new AtomicInteger();

    @Override
    public void run() {
        for (int j=0;j<100000;j++){
            i.incrementAndGet();
         /*  synchronized (Atomic01Example.class) {
               i++;
           }*/

        }
    }
    public static void main(String[] args) throws InterruptedException {
        Atomic01Example sync01=new Atomic01Example();
        Atomic01Example sync02=new Atomic01Example();
        Thread thread1=new Thread(sync01);
        Thread thread2=new Thread(sync02);
        thread1.start();
        thread2.start();
        //thread1.join();
        //thread2.join();
        Thread.sleep(1000);
        System.out.println(i);


    }



}
