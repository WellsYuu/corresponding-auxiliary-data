package com.tl.executor.thread;/*
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

public class WukongThread2 implements    Runnable{

    private String name;

    public WukongThread2(String name) {

        this.name = name;
    }

    @Override
    public void run() {

        System.out.println("1");
       try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {


        }
        for (int i=0;i<1000000;i++){

            System.out.println(i);
        }

        System.out.println(name+"说大家好");
    }

    public static void main(String[] args) {
        //new WukongThread2("T1").start();

        new Thread(new WukongThread2("T2")).start();
    }

}
