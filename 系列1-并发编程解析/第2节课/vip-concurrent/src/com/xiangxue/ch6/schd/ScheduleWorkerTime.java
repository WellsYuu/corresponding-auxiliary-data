package com.xiangxue.ch6.schd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：定时任务的工作类
 */
public class ScheduleWorkerTime implements Runnable{
    public final static int Long_8 = 8;//任务耗时8秒
    public final static int Short_2 = 2;//任务耗时2秒
    public final static int Normal_5 = 5;//任务耗时5秒

    public static SimpleDateFormat formater = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static AtomicInteger count = new AtomicInteger(0);
    
    @Override
    public void run() {
    	if(count.get()==0) {
            System.out.println("Long_8....begin:"+formater.format(new Date()));
            SleepTools.second(Long_8);
            System.out.println("Long_8....end:"+formater.format(new Date())); 
            count.incrementAndGet();
    	}else if(count.get()==1) {
    		System.out.println("Short_2 ...begin:"+formater.format(new Date()));
    		SleepTools.second(Short_2);
    		System.out.println("Short_2 ...end:"+formater.format(new Date()));
            count.incrementAndGet();    		
    	}else {
    		System.out.println("Normal_5...begin:"+formater.format(new Date()));
    		SleepTools.second(Normal_5);
    		System.out.println("Normal_5...end:"+formater.format(new Date()));
    		count.incrementAndGet(); 
    	}
    }
    
	public static void main(String[] args) {
	    	ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
	    	//任务间隔6秒
	        schedule.scheduleAtFixedRate(new ScheduleWorkerTime(),
	                0, 6000, TimeUnit.MILLISECONDS);
	}
}
