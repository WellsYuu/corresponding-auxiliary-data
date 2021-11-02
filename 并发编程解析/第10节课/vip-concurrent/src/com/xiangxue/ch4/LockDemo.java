package com.xiangxue.ch4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 *
 *使用显示锁的范式
 */
public class LockDemo {
	
	private Lock lock  = new ReentrantLock();
	private int count;
	
	public void increament() {
		lock.lock();
		try {
			count++;
		}finally {
			lock.unlock();
		}
	}
	
	public synchronized void incr2() {
		count++;
		incr2();
	}
	
	public synchronized void test3() {
		incr2();
	}

}
