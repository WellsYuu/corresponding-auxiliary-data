package com.xiangxue.ch1.wn;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：快递实体类
 */
public class Express {
    public final static String CITY = "ShangHai";
    private int km;/*快递运输里程数*/
    private String site;/*快递到达地点*/

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /* 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理*/
    public synchronized void changeKm(){
    	this.km = 101;
    	notifyAll();
    	//其他的业务代码

    }

    /* 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理*/
    public synchronized void changeSite(){
    	this.site = "BeiJing";
    	notify();
    }

    public synchronized void waitKm(){
    	while(this.km<=100) {
    		try {
				wait();
				System.out.println("check km thread["+Thread.currentThread().getId()
						+"] is be notifed.");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	System.out.println("the km is"+this.km+",I will change db.");

    }

    public synchronized void waitSite(){
    	while(CITY.equals(this.site)) {
    		try {
				wait();
				System.out.println("check site thread["+Thread.currentThread().getId()
						+"] is be notifed.");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	System.out.println("the site is"+this.site+",I will call user.");
    }
}
