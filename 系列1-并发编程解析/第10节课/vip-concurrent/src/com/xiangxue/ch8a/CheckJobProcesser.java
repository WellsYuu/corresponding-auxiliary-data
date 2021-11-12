package com.xiangxue.ch8a;

import java.util.Map;
import java.util.concurrent.DelayQueue;

import com.xiangxue.ch5.bq.ItemVo;
import com.xiangxue.ch8a.vo.JobInfo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：任务完成后,在一定的时间供查询，之后为释放资源节约内存，需要定期处理过期的任务
 */
public class CheckJobProcesser {
    private static DelayQueue<ItemVo<String>> queue 
    	= new DelayQueue<ItemVo<String>>();//存放任务的队列
    
    //处理队列中到期任务的线程
    private static class FetchJob implements Runnable{

		@Override
		public void run() {
			
		}

    }
    
    /*任务完成后，放入队列，经过expireTime时间后，从整个框架中移除*/
    public void putJob(String jobName,long expireTime) {

    }
    
    
}
