package com.xiangxue.ch8a;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiangxue.ch8a.vo.*;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 * 框架的主体类，也是调用者主要使用的类
 */
public class PendingJobPool {
	
	//对工作中的任务进行包装，提交给线程池使用，并处理任务的结果，写入缓存以供查询
	private static class PendingTask<T,R> implements Runnable{
		
		private JobInfo<R> jobInfo;
		private T processData;

		public PendingTask(JobInfo<R> jobInfo, T processData) {
			super();
			this.jobInfo = jobInfo;
			this.processData = processData;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			R r = null;

		}
	}
	
	//调用者提交工作中的任务
	public <T,R> void putTask(String jobName,T t) {
		
	}
	
	//调用者注册工作，如工作名，任务的处理器等等
	public <R> void registerJob(String jobName,int jobLength,
			ITaskProcesser<?, ?> taskProcesser,long expireTime) {
		
		
	}
	
	//根据工作名称检索工作
	@SuppressWarnings("unchecked")
	private <R> JobInfo<R> getJob(String jobName){
		
		return null;
	}
	
	//获得每个任务的处理详情
	public <R> List<TaskResult<R>> getTaskDetail(String jobName){
		
		return null;
	}
	
	//获得工作的整体处理进度
	public <R> String getTaskProgess(String jobName) {
		
		return null;	
	}
	
}
