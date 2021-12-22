package com.tl.api.curator.atomic;/*
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

import com.tl.api.curator.lock.CuratorInterProcessMutex;

public class LockTest implements  Runnable {
   // static  int i=0;
    static CuratorAtomic i=new CuratorAtomic("/root");

    @Override
    public void run() {
        try {
            for(int j=0;j<1000000;j++){
                //i++;
                i.increment();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        LockTest lockTest=new LockTest();
        Thread thread=new Thread(lockTest);
        Thread thread2=new Thread(lockTest);
        thread.start();thread2.start();
        thread.join();thread2.join();
        System.out.println("success:" + i.get().succeeded() + ";before:" + i.get().preValue() + ";after:" + i.get().postValue());
        System.out.println(i.get().postValue());

    }



}
