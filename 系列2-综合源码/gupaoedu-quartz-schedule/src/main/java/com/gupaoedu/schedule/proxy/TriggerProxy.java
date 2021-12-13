package com.gupaoedu.schedule.proxy;

import java.lang.reflect.Method;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gupaoedu.schedule.entity.Task;

/**
 * 
 * @author Tom
 *
 */
public class TriggerProxy implements Job{
	
	public static final String DATA_TARGET_KEY = "target";		//目标对象，实例
	public static final String DATA_TRIGGER_KEY = "trigger";	//方法名
	public static final String DATA_TRIGGER_PARAMS_KEY = "trigger_params";//方法的参数值
	public static final String DATA_TASK_KEY = "task";			//自己封装的任务对象
	
	private ThreadLocal<Entry> local = new ThreadLocal<Entry>();
	
	
	//是由调度器自动调用的
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		TriggerProxy.class.getResource("")
		try {
			local.set(new Entry());
			//获取参数信息
			JobDataMap data = context.getTrigger().getJobDataMap();
			Object target = data.get(DATA_TARGET_KEY);
			Method method = (Method)data.get(DATA_TRIGGER_KEY);
			Object[] params = (Object[])data.get(DATA_TRIGGER_PARAMS_KEY);
			
			//修改任务执行次数
			Task task = (Task)data.get(DATA_TASK_KEY);
			//任务没执行一次，需要累加1
			task.setExecute(task.getExecute() + 1);
			
			local.get().start = System.currentTimeMillis();
			
			
			//调用触发器，用反射调用我们自己定义的方法
			method.invoke(target,params);
			
			local.get().end = System.currentTimeMillis();
			
			//记录任务的最后一次执行时间
			task.setLastExeTime(local.get().start);
			//记录任务完成的时间
			task.setLastFinishTime(local.get().end);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	class Entry{
		public long start = 0L;
		public long end = 0L;
	}
	
}
