package com.xiangxue.ch8a;

import java.util.Map;
import java.util.concurrent.DelayQueue;

import com.xiangxue.ch5.bq.ItemVo;
import com.xiangxue.ch5.bq.Order;
import com.xiangxue.ch8a.vo.JobInfo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：任务完成后,在一定的时间供查询，之后为释放资源节约内存，需要定期处理过期的任务
 */
public class CheckJobProcesser {
    private static DelayQueue<ItemVo<String>> queue 
    	= new DelayQueue<ItemVo<String>>();//存放已完成任务等待过期的队列
    
	//单例模式------
	private CheckJobProcesser() {}
	
	private static class ProcesserHolder{
		public static CheckJobProcesser processer = new CheckJobProcesser();
	}
	
	public static CheckJobProcesser getInstance() {
		return ProcesserHolder.processer;
	}
	//单例模式------    
    
    //处理队列中到期任务的实行
    private static class FetchJob implements Runnable{

		@Override
		public void run() {
			while(true) {
				try {
					//拿到已经过期的任务
					ItemVo<String> item = queue.take();
					String jobName =  (String)item.getDate();
					PendingJobPool.getMap().remove(jobName);
					System.out.println(jobName+" is out of date,remove from map!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
    }
    
    /*任务完成后，放入队列，经过expireTime时间后，从整个框架中移除*/
    public void putJob(String jobName,long expireTime) {
    	ItemVo<String> item = new ItemVo<String>(expireTime,jobName);
    	queue.offer(item);
    	System.out.println("Job["+jobName+"已经放入了过期检查缓存，过期时长："+expireTime);
    }
    
    static {
    	Thread thread = new Thread(new FetchJob());
    	thread.setDaemon(true);
    	thread.start();
    	System.out.println("开启任务过期检查守护线程................");
    }
    
    
}
