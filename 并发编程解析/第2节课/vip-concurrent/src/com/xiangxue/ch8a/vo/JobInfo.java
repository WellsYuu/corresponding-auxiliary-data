package com.xiangxue.ch8a.vo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import com.xiangxue.ch8a.CheckJobProcesser;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：提交给框架执行的工作实体类,工作：表示本批次需要处理的同性质任务(Task)的一个集合
 */
public class JobInfo<R> {
	//区分唯一的工作
	private final String jobName;
	//工作的任务个数
	private final int jobLength;
	//这个工作的任务处理器
	private final ITaskProcesser<?,?> taskProcesser;
	//成功处理的任务数
	private final AtomicInteger  successCount;
	//已处理的任务数
	private final AtomicInteger  taskProcesserCount;
	//结果队列，拿结果从头拿，放结果从尾部放
	private final LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;
	//工作的完成保存的时间，超过这个时间从缓存中清除
	private final long expireTime;
	
	//与课堂上有不同，修订为，阻塞队列不应该由调用者传入，应该内部生成，长度为工作的任务个数
	public JobInfo(String jobName, int jobLength, 
			ITaskProcesser<?, ?> taskProcesser,
			long expireTime) {
		super();
		this.jobName = jobName;
		this.jobLength = jobLength;
		this.taskProcesser = taskProcesser;
		this.successCount = new AtomicInteger(0);
		this.taskProcesserCount = new AtomicInteger(0);
		this.taskDetailQueue = new LinkedBlockingDeque<TaskResult<R>>(jobLength);;
		this.expireTime = expireTime;
	}

	public ITaskProcesser<?, ?> getTaskProcesser() {
		return taskProcesser;
	}

	//返回成功处理的结果数
	public int getSuccessCount() {
		return successCount.get();
	}

	//返回当前已处理的结果数
	public int getTaskProcesserCount() {
		return taskProcesserCount.get();
	}
	
	//提供工作中失败的次数，课堂上没有加，只是为了方便调用者使用
	public int getFailCount() {
		return taskProcesserCount.get() - successCount.get();
	}
	
	public String getTotalProcess() {
		return "Success["+successCount.get()+"]/Current["
				+taskProcesserCount.get()+"] Total["+jobLength+"]";
	}
	
	//获得工作中每个任务的处理详情
	public List<TaskResult<R>> getTaskDetail(){
		List<TaskResult<R>> taskList = new LinkedList<>();
		TaskResult<R> taskResult;
		//从阻塞队列中拿任务的结果，反复取，一直取到null为止，说明目前队列中最新的任务结果已经取完，可以不取了
		while((taskResult=taskDetailQueue.pollFirst())!=null) {
			taskList.add(taskResult);
		}
		return taskList;
	}
	
	//放任务的结果，从业务应用角度来说，保证最终一致性即可，不需要对方法加锁.
	public void addTaskResult(TaskResult<R> result,CheckJobProcesser checkJob) {
		if (TaskResultType.Success.equals(result.getResultType())) {
			successCount.incrementAndGet();
		}
		taskDetailQueue.addLast(result);
		taskProcesserCount.incrementAndGet();
		
		if(taskProcesserCount.get()==jobLength) {
			checkJob.putJob(jobName, expireTime);
		}
		
	}

	
	
	


}
