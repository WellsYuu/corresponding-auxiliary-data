package com.gupaoedu.schedule.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.gupaoedu.schedule.entity.Task;
import com.gupaoedu.schedule.proxy.TriggerProxy;
import com.gupaoedu.schedule.service.IScheduleService;


/**
 * 任务管理，负责输出和管理日志
 * @author Tom
 */
@Service
public class ScheduleService implements IScheduleService,ApplicationContextAware{

	//自己不能再重新搞一个工厂出来，必须和Spring引用的调度工厂是同一个
	//才能实现无缝集成，而且可以动态修改配置文件已经配置好的任务
	@Autowired private SchedulerFactoryBean schedulerFactory;
	
	//保存所有任务表达式的描述
	private Map<String,String> cronDesc = new LinkedHashMap<String,String>(); 
	
	//所有的任务都放到任务池中
	private static Map<String,Task> taskPool = new LinkedHashMap<String,Task>();
	
	private static ApplicationContext app;
	
	public ScheduleService(){}
	
	/**
	 * 创建一个调度任务
	 * @param m
	 * @return
	 * @throws Exception
	 */
	private Task createTask(Method m) throws Exception{
		
		//任务ID就是时间戳，再加上一个随机数
		Task task = new Task("" + System.currentTimeMillis());
		
		//通过方法，可以获取到方法所在的class
		Class clazz = m.getDeclaringClass();
		//设置任务组
		task.setGroup(clazz.getName());
		
		//设置触发器信息
		//一个触发器对应一个方法，在此处，所谓的触发器还只是一个概念
		//概念是一个字符描述，它记录了触发的目标信息
		task.setTrigger(clazz.getName() + "." + m.getName());
		
		Annotation sc = m.getAnnotation(Scheduled.class);
		Method cronM = sc.getClass().getMethod("cron",null);
		String cron = cronM.invoke(sc, null).toString();
		task.setTriggerDesc(cronDesc.get(cron));
		task.setCron(cron);
		task.setCronDesc(cronDesc.get(cron));

		
		//此时，就已经完成一个task的封装
		return task;
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext app) throws BeansException {
		this.app = app;
		
		//加载所有任务到任务队列
 		for(String name : app.getBeanDefinitionNames()){
			try{
				Class<?> c = app.getBean(name).getClass();
				for(Method m : c.getMethods()){
					//只要是添加了Scheduled注解的都给他添加到调度工厂中
					if(m.isAnnotationPresent(Scheduled.class)){
						Task task = createTask(m);
						//将任务加入队列准备启动
						createTask(task);
					}
				}
			}catch(Exception e){
				continue;
			}
		}
	}
	
	public Task getTask(String taskId) {
		return taskPool.get(taskId);
	}
	
	@Override
	public List<Task> getAllTask() {
		if(taskPool.size() == 0){
			return new ArrayList<Task>();
		}
		List<Task> r = new ArrayList<Task>();
		r.addAll(taskPool.values());
		return r;
	}

	private Task createTask(Task task) throws Exception{
		
		if(null == task.getGroup() || task.getGroup().trim().length() == 0){ return null; }
		
		//还是拿到字节码
		Class<?> clazz = Class.forName(task.getGroup());
		//先从容器中获取
		Object target = null;
		try{
			//从Spring容器中提取已经创建好的对象引用
			target = app.getBean(clazz);
		}catch(Exception e){
			
		}
		//如果Spring容器没有帮我们创建，那么就自己创建实例
		if(target == null){
			target = clazz.newInstance();
		}
		
		//把触发器需要调用的方法找出来，还是用反射
		Method m = clazz.getMethod(task.getTrigger().replaceAll(task.getGroup() + ".", ""));
		
		//把任务ID取出来，时间戳
		String taskId = task.getId();
		
		//================ 事前准备  ====================
		
		//拿到Quartz中的调度器
		Scheduler sched = schedulerFactory.getScheduler();
		
		//创建一个Detail
        JobDetail taskDetail = new JobDetail(taskId, task.getGroup(), TriggerProxy.class);// 任务名，任务组，任务执行类  
        // 触发器
        CronTrigger trigger = new CronTrigger(taskId, task.getTrigger());// 触发器名,触发器组 
        
        //在这里设置CronExpression表达式
        trigger.setCronExpression(task.getCron());// 触发器时间设定  
        
        //JobDataMap   用来存储附加信息
        //利用这么一个API，把自定义的信息添加到Map中
        trigger.getJobDataMap().put(TriggerProxy.DATA_TARGET_KEY, target);
        trigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_KEY, m);
        
//        m.getParameterTypes()
        
        trigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_PARAMS_KEY, new Object[]{});
        trigger.getJobDataMap().put(TriggerProxy.DATA_TASK_KEY, task);
        sched.scheduleJob(taskDetail, trigger);
        // 如果这个任务没有被主动关闭，我们就给他启动
        if (!sched.isShutdown()) {  
            sched.start();  
        }
        
        
        //放入我们的任务池
        if(!taskPool.containsKey(taskId)){
        	taskPool.put(taskId, task);
		}
        return task;
	}
	
	
	/** 
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
     */  
    public Task createTask(String taskName,String taskClassName,String triggerName,String cron) throws Exception{
    	return createTask(taskName,null,taskClassName,null,triggerName,cron);
    }
  
    /** 
     * 添加一个定时任务 
     */  
    private Task createTask(String taskName, String taskGroupName, String taskClassName,String triggerGroupName, String triggerName,String cron)  throws Exception{  
    	//根据类名，利用反射机制获取到类的字节码
    	//约定优于配置
    	
    	Class<?> clazz = Class.forName(taskClassName); //就是类名全程，包名.类名
    	Method m = clazz.getMethod(triggerName);		//显然就是方法名
    	
    	Task task = createTask(m);
    	task.setName(taskName);
    	if(null != taskGroupName){
    		task.setGroup(taskGroupName);
    	}
    	task.setCron(cron);
    	return createTask(task);
    }
  
    /** 
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
     *  
     */  
    public Task modifyTaskCron(String taskId, String cron) {  
    	
    	Task task = taskPool.get(taskId);
        try {
        	
            Scheduler sched = schedulerFactory.getScheduler();  
            CronTrigger trigger = (CronTrigger) sched.getTrigger(taskId,task.getTrigger());  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(cron)) {  
                JobDetail taskDetail = sched.getJobDetail(taskId,task.getGroup());  
                Class objJobClass = taskDetail.getJobClass();
                
                removeTask(taskId);
                
                //重新生成ID
                task.setId("" + System.currentTimeMillis());
                task.setCron(cron);
                createTask(task);
                
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }
        return task;
        
    }
  
  
    /** 
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     *  
     */  
    public Task removeTask(String taskId) {  
    	
    		Task task = taskPool.get(taskId);
        try {
            Scheduler sched = schedulerFactory.getScheduler();  
            sched.pauseTrigger(taskId, task.getTrigger());// 停止触发器  
            sched.unscheduleJob(taskId, task.getGroup());// 移除触发器  
            sched.deleteJob(taskId, task.getGroup());// 删除任务 
            
            taskPool.remove(taskId);
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }
        return task;
        
    }
  
    
    /**
     * 暂停任务
     * @param taskId
     */
    public Task pauseTask(String taskId){
    		Task task = taskPool.get(taskId);
    		try {
            Scheduler sched = schedulerFactory.getScheduler();  
            sched.pauseTrigger(task.getId(), task.getTrigger());// 停止触发器  
            
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }
    		return task;
    }
    
    
    /** 
     * 
     *  
     */
    public Task restartTask(String taskId) {
    		Task task = taskPool.get(taskId);
		try {
			Scheduler sched = schedulerFactory.getScheduler();  
		      // 重启触发器  
			sched.resumeTrigger(task.getId(),task.getTrigger());  
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		} 
        return task;
    }
    
    
    /**
     * 关闭任务
     * @param taskId
     */
    public Task shutdownTask(String taskId){
    		Task task = taskPool.get(taskId);
    	 	try {
             Scheduler sched = schedulerFactory.getScheduler();  
             sched.pauseTrigger(taskId, task.getTrigger());// 停止触发器  
             sched.unscheduleJob(taskId, task.getGroup());// 移除触发器  
             sched.deleteJob(taskId, task.getGroup());// 删除任务 
             
         } catch (Exception e) {  
             throw new RuntimeException(e);  
         }
    	 	return task;
    }
    
    /** 
     * 启动所有定时任务 
     *  
     */
    public void startAllTask() {  
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            sched.start();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }
    }
  
    /** 
     * 关闭所有定时任务 
     */  
    public void shutdownAllTask() {  
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            if (!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } 
    }

}
