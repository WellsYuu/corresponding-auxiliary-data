package com.xiangxue.ch4.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class TrinityLock   {

//    public void lock() {
//        sync.acquireShared(1);
//    }
//
//    public void unlock() {
//        sync.releaseShared(1);
//    }
//
//    public void lockInterruptibly() throws InterruptedException {
//        sync.acquireSharedInterruptibly(1);
//    }
//
//    public boolean tryLock() {
//        return sync.tryAcquireShared(1) >= 0;
//    }
//
//    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
//        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
//    }
//
//    @Override
//    public Condition newCondition() {
//        return sync.newCondition();
//    }
}
