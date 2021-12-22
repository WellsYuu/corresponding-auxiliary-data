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

public class Sync021 implements  Runnable{
   static  int i=0;
    @Override
    public void run() {
        for (int j=0;j<100000;j++){
            add();

        }
    }

    public  synchronized     void add(){
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        Sync021 sync02= new Sync021();
        Thread thread1=new Thread(sync02);
        Thread thread2=new Thread(sync02);
        thread1.start();
        thread2.start();
        thread1.join();thread2.join();
        System.out.println(i);


    }



}
