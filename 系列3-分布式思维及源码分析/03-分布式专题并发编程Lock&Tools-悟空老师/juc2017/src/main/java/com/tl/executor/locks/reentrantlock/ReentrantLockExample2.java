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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample2  implements  Runnable{

    ReentrantLock lock=new ReentrantLock();
    @Override
    public void run() {
        try {
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                TimeUnit.SECONDS.sleep(6);
                System.out.println("拿到这个锁");
            }else
            {
                System.out.println("获取失败");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            if(lock.isHeldByCurrentThread())
            lock.unlock();

        }

    }



    public static void main(String[] args) {
        ReentrantLockExample2 reentrantLockExample2 =new ReentrantLockExample2();
        new Thread(reentrantLockExample2).start();
        new Thread(reentrantLockExample2).start();


    }


}
