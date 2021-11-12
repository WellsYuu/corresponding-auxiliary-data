package com.xiangxue.ch5.bq;

import java.util.concurrent.DelayQueue;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：取出到期订单的功能
 */
public class FetchOrder implements Runnable {
	
	private DelayQueue<ItemVo<Order>> queue;
	
	public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
		super();
		this.queue = queue;
	}	

	@Override
	public void run() {
		while(true) {
			try {
				ItemVo<Order> item = queue.take();
				Order order = (Order)item.getDate();
				System.out.println("get from queue:"+order.getOrderNo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
}
