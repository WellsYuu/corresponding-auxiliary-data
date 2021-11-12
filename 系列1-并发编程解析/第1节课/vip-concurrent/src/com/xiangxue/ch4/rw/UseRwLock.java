package com.xiangxue.ch4.rw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class UseRwLock implements GoodsService {
	
    private GoodsInfo goodsInfo;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock getLock = lock.readLock();//读锁
    private final Lock setLock = lock.writeLock();//写锁

    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

	@Override
	public GoodsInfo getNum() {
		getLock.lock();
		try {
			SleepTools.ms(5);
			return this.goodsInfo;
		}finally {
			getLock.unlock();
		}
		
	}

	@Override
	public void setNum(int number) {
		setLock.lock();
		try {
			SleepTools.ms(5);
			goodsInfo.changeNumber(number);
		}finally {
			setLock.unlock();
		}
	}

}
