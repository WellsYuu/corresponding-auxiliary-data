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
public class SelfLock {

//    // 仅需要将操作代理到Sync上即可
//    private final Sync sync = new Sync();
//
//    public void lock() {
//        sync.acquire(1);
//    }
//
//    public boolean tryLock() {
//        return sync.tryAcquire(1);
//    }
//
//    public void unlock() {
//        sync.release(1);
//    }
//
//    public Condition newCondition() {
//        return sync.newCondition();
//    }
//
//    public boolean isLocked() {
//        return sync.isHeldExclusively();
//    }
//
//    public boolean hasQueuedThreads() {
//        return sync.hasQueuedThreads();
//    }
//
//    public void lockInterruptibly() throws InterruptedException {
//        sync.acquireInterruptibly(1);
//    }
//
//    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
//        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
//    }
}
