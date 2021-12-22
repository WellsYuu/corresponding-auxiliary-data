package com.tl.executor.locks;/*
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

public class Sync03 implements  Runnable{
   static int i=0;
    @Override
    public void run() {
        for (int j=0;j<100000;j++){
           synchronized (Sync03.class) {
               i++;
           }

        }
    }
    public static void main(String[] args) throws InterruptedException {

            Thread thread1=new Thread(new Sync03());
            Thread thread2=new Thread(new Sync03());
            thread1.start();
            thread2.start();
            thread1.join();thread2.join();
            System.out.println(i);


    }



}
