package com.xiangxue.ch7.tranfer.service;

import java.util.Random;

import com.xiangxue.ch7.tranfer.UserAccount;
import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：不会产生死锁的安全转账第二种方法，尝试拿锁
 */
public class SafeOperateToo implements ITransfer {

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount)
            throws InterruptedException {
    	Random r = new Random();
    	while(true) {
    		if(from.getLock().tryLock()) {
    			try {
    				System.out.println(Thread.currentThread().getName()
    						+" get "+from.getName());
    				if(to.getLock().tryLock()) {
    					try {
    	    				System.out.println(Thread.currentThread().getName()
    	    						+" get "+to.getName());    						
    						//两把锁都拿到了
    	                    from.flyMoney(amount);
    	                    to.addMoney(amount);
    	                    break;
    					}finally {
    						to.getLock().unlock();
    					}
    				}
    			}finally {
    				from.getLock().unlock();
    			}
    		}
    		SleepTools.ms(r.nextInt(10));
    	}
    }
}
