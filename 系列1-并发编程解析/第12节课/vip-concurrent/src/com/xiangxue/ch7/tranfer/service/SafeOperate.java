package com.xiangxue.ch7.tranfer.service;

import com.xiangxue.ch7.tranfer.UserAccount;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：不会产生死锁的安全转账
 */
public class SafeOperate implements ITransfer {
	private static Object tieLock = new Object();//加时赛锁

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount)
            throws InterruptedException {
    	
    	int fromHash = System.identityHashCode(from);
    	int toHash = System.identityHashCode(to);
    	//先锁hash小的那个
    	if(fromHash<toHash) {
            synchronized (from){
                System.out.println(Thread.currentThread().getName()
                		+" get"+from.getName());
                Thread.sleep(100);
                synchronized (to){
                    System.out.println(Thread.currentThread().getName()
                    		+" get"+to.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }    		
    	}else if(toHash<fromHash) {
            synchronized (to){
                System.out.println(Thread.currentThread().getName()
                		+" get"+to.getName());
                Thread.sleep(100);
                synchronized (from){
                    System.out.println(Thread.currentThread().getName()
                    		+" get"+from.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }    		
    	}else {//解决hash冲突的方法
    		synchronized (tieLock) {
				synchronized (from) {
					synchronized (to) {
	                    from.flyMoney(amount);
	                    to.addMoney(amount);						
					}
				}
			}
    	}
    	
    }
}
