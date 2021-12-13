package com.gupaoedu.schedule.service;

import java.util.List;

import com.gupaoedu.schedule.entity.Task;

public interface IScheduleService {
	
	/**
	 * 获取任务列表
	 * @return
	 */
	public List<Task> getAllTask();
	
	/**
	 * 根据任务ID获取一个任务
	 * @return
	 */
	public Task getTask(String taskId);
	
	/**
	 * 新建一个任务
	 * @param taskName 任务名称
	 * @param taskClassName	任务Class名称
	 * @param triggerName 触发器名称
	 * @param cron	执行表达式
	 * @throws Exception
	 */
	public Task createTask(String taskName,String taskClassName,String triggerName,String cron) throws Exception;
  
    /** 
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
     *  
     */  
    public Task modifyTaskCron(String taskId, String cron);
  
    /** 
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     *  
     */  
    public Task removeTask(String taskId);
  
    /**
     * 重启任务
     * @param taskId
     * @return
     */
    public Task restartTask(String taskId);
    
    
    /**
     * 暂停定时任务
     * @param taskId
     * @return
     */
    public Task pauseTask(String taskId);
    
    /**
     * 关闭定时任务
     * @param taskId
     * @return
     */
    public Task shutdownTask(String taskId);
    
    
    /** 
     * 启动所有定时任务 
     *  
     */
    public void startAllTask();
  
    /** 
     * 关闭所有定时任务 
     */  
    public void shutdownAllTask();
	
}
