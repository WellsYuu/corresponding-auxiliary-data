/*
package com.tl.executor.locks.semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SemaphoreWarpper {

	private static final Logger log = LoggerFactory.getLogger(SemaphoreWarpper.class.getName());

	private volatile int queueCount = 20;

	public SemaphoreWarpper(int queueCount) {
		this.queueCount = queueCount;
		semaphore = new Semaphore(this.queueCount);
	}

	protected Semaphore semaphore;

	protected ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	protected AtomicInteger blockingCount = new AtomicInteger(0);

	public <T> T execute(Callable<T> r, String methodName, int timeout) throws Exception {
		T obj = null;
		lock.readLock().lockInterruptibly();
		try {
			blockingCount.addAndGet(1);
			// 尝试锁定
			if (semaphore.tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
				blockingCount.addAndGet(-1);
				try {
					obj = r.call();
				} finally {
					semaphore.release();
				}
			} else {
				blockingCount.addAndGet(-1);
				if (log.isInfoEnabled()) {
					log.info("queueCount:" + getQueueCount() + ",BlockingCount:" + blockingCount.get());
				}
				throw new SemaphoreGetException("获取信号量失败!");
			}
		} finally {
			lock.readLock().unlock();
		}
		return obj;
	}

	*/
/**
	 * 重置信号数量
	 *//*

	public void resetSemaphore(int count) {
		lock.writeLock().lock();
		try {
			this.queueCount = count;
			semaphore = new Semaphore(count);
		} catch (Exception e) {
			log.error("重置信号数量操作异常.", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public AtomicInteger getBlockingCount() {
		return blockingCount;
	}

	public int getQueueCount() {
		return queueCount;
	}

}
*/
