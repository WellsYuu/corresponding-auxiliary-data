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
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class Atomic05Example {

    public static final AtomicIntegerFieldUpdater<Wukong> atomicInteger= AtomicIntegerFieldUpdater.newUpdater(Wukong.class,"score");


    public static void main(String[] args) {
        final Wukong wukong=new Wukong();
        final Atomic05Example atomic04Example=new Atomic05Example();
        TljucUtil.timeTasks(100, 1, new Runnable() {
            @Override
            public void run() {
                atomicInteger.incrementAndGet(wukong);
            }
        });
        System.out.println("score:"+wukong.score);
    }


}
class Wukong
{

    volatile int score;//统计


}