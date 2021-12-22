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
 */

public class VolatileExample {
    int x = 0;
    volatile  boolean v = false;
    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        while (!v) {
            //uses x - guaranteed to see 42.
            //输出或者sleep 一段时间能拿到v等于true JIT优化 副本会（读）会写数据给主内存
        }
        System.out.println("悟空是个猴子");

    }

    public static void main(String[] args) throws InterruptedException {
       final VolatileExample volatileExample=new VolatileExample();
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
            }
        });
        thread2.start();



    }

}