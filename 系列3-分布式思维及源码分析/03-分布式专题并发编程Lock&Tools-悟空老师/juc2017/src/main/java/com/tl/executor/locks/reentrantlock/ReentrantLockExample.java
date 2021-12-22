package com.tl.executor.locks.reentrantlock;/*
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

import com.tl.executor.locks.Sync01;
import com.tl.executor.locks.Sync02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample implements  Runnable {
    Lock lock=new ReentrantLock();
    static int i=0;

    @Override
    public void run() {
        lock.lock();
        try {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        } finally {

            lock.unlock();
        }
    }



    public void a() {
        lock.lock();
        b();
        lock.unlock();


    }

    public void b() {
        lock.lock();
        lock.lock();
        lock.lock();
        System.out.println("悟空是只猴子");
        lock.unlock();
        lock.unlock();
        lock.unlock();
    }


    public static void main(String[] args) throws InterruptedException {
        ReentrantLockExample reentrantLockExample = new ReentrantLockExample();
        reentrantLockExample.a();
     /*   Thread thread1 = new Thread(new ReentrantLockExample());
        Thread thread2 = new Thread(new ReentrantLockExample());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(i);*/


    }
    }
