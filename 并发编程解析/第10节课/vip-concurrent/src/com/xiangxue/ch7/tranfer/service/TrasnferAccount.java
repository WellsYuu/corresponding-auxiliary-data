package com.xiangxue.ch7.tranfer.service;

import com.xiangxue.ch7.tranfer.UserAccount;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：不安全的转账动作的实现
 */
public class TrasnferAccount implements ITransfer {
	
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) 
    		throws InterruptedException {
        synchronized (from){//先锁转出
            System.out.println(Thread.currentThread().getName()
            		+" get"+from.getName());
            Thread.sleep(100);
            synchronized (to){//再锁转入
                System.out.println(Thread.currentThread().getName()
                		+" get"+to.getName());
                from.flyMoney(amount);
                to.addMoney(amount);
            }
        }
    }
}
