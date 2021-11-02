package com.xiangxue.ch6.schd;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：演示ScheduledThreadPoolExecutor的用法
 */
public class ScheduledCase {
    public static void main(String[] args) {
    	
    	ScheduledThreadPoolExecutor schedule 
    		= new ScheduledThreadPoolExecutor(1);
    	
    	schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HasException), 
    			1000, 3000, TimeUnit.MILLISECONDS);
    	schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.Normal), 
    			1000, 3000, TimeUnit.MILLISECONDS);    	
    	
    }
}
