package com.tl.executor.locks.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	public void await(){
		try {
			lock.lock();
			System.out.println( Thread.currentThread().getName() + "睡觉");
			Thread.sleep(1000);
			condition.await();
			System.out.println( Thread.currentThread().getName() +"醒了");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void singnal(){
		try {
			lock.lock();
			System.out.println(Thread.currentThread().getName() + "快起来");
			Thread.sleep(1000);
			condition.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		final ConditionExample conditionExample = new ConditionExample();
		 new Thread(new Runnable() {
				@Override
				public void run() {
					conditionExample.await();
				}
			}, "猪>").start();
			Thread.sleep(100);
			new Thread(new Runnable() {
				@Override
				public void run() {
					conditionExample.singnal();
				}
			}, "催眠师>").start();

	}
	
	
	
}
