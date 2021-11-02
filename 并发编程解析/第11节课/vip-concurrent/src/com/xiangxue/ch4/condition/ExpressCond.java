package com.xiangxue.ch4.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class ExpressCond {
    public final static String CITY = "ShangHai";
    private int km;/*快递运输里程数*/
    private String site;/*快递到达地点*/
    private Lock lock = new ReentrantLock();
    private Condition keCond = lock.newCondition();
    private Condition siteCond = lock.newCondition();

    public ExpressCond() {
    }

    public ExpressCond(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /* 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理*/
    public void changeKm(){
        lock.lock();
        try {
        	this.km = 101;
        	keCond.signalAll();
        }finally {
        	lock.unlock();
        }
    }

    /* 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理*/
    public  void changeSite(){
    	lock.lock();
        try {
        	this.site = "BeiJing";
        	siteCond.signal();
        }finally {
        	lock.unlock();
        }    	
    }

    /*当快递的里程数大于100时更新数据库*/
    public void waitKm(){
    	lock.lock();
    	try {
        	while(this.km<=100) {
        		try {
        			keCond.await();
    				System.out.println("check km thread["+Thread.currentThread().getId()
    						+"] is be notifed.");
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}    		
    	}finally {
    		lock.unlock();
    	}

        System.out.println("the Km is "+this.km+",I will change db");
    }

    /*当快递到达目的地时通知用户*/
    public void waitSite(){
    	lock.lock();
        try {
        	while(CITY.equals(this.site)) {
        		try {
        			siteCond.await();
    				System.out.println("check site thread["+Thread.currentThread().getId()
    						+"] is be notifed.");
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}
        }finally {
        	lock.unlock();
        } 
        System.out.println("the site is "+this.site+",I will call user");
    }
}
