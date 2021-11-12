package com.xiangxue.ch5.bq;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：存放到队列的元素
 */
public class ItemVo<T> implements Delayed{
	
	private long activeTime;//到期时间，单位毫秒
	private T date;
	
	//activeTime是个过期时长
	public ItemVo(long activeTime, T date) {
		super();
		this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime, 
				TimeUnit.MILLISECONDS)+System.nanoTime();//将传入的时长转换为超时的时刻
		this.date = date;
	}
	
	public long getActiveTime() {
		return activeTime;
	}

	public T getDate() {
		return date;
	}

	//按照剩余时间排序
	@Override
	public int compareTo(Delayed o) {
		long d = getDelay(TimeUnit.NANOSECONDS)-o.getDelay(TimeUnit.NANOSECONDS);
		return (d==0)?0:((d>0)?1:-1);
	}

	//返回元素的剩余时间
	@Override
	public long getDelay(TimeUnit unit) {
		long d = unit.convert(this.activeTime-System.nanoTime(),
				TimeUnit.NANOSECONDS);
		return d;
	}
	



}
