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

import com.tl.executor.TljucUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class Atomic04Example {

     AtomicInteger atomicInteger=new AtomicInteger();
    /**只保证一个共享变量原子操作 **/
    public AtomicInteger add(){

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        atomicInteger.addAndGet(1);
        atomicInteger.addAndGet(2);
        atomicInteger.addAndGet(3);
        atomicInteger.addAndGet(4);
        return  atomicInteger;
    }
    public static void main(String[] args) {
        final Atomic04Example atomic04Example=new Atomic04Example();
        TljucUtil.timeTasks(100, 1, new Runnable() {
            @Override
            public void run() {
                System.out.println(atomic04Example.add());;
            }
        });
        System.out.println("输出:"+ atomic04Example.atomicInteger );
    }
}
