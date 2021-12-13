package com.gupaoedu.schedule.entity;



/**
 * 调度任务定义
 * @author Tom
 *
 */
public class Task {
	
	private String id;				//任务ID,默认系统时间戳
	private String parentId = "";	//父级任务ID
	private String name = "";		//任务名称
	private String desc = "";		//任务描述
	private int planExe = 0;		//计划执行次数,默认为0，表示满足条件循环执行
	private String group = "";		//任务组名称
	private String groupDesc = "";	//任务组描述
	private String cron = "";		//任务表达式
	private String cronDesc = "";	//表达式描述
	private String trigger = "";	//触发器
	private String triggerDesc = "";//触发器描述
	private int execute = 0;		//任务被执行过多少次
	private Long lastExeTime = 0L;	//最后一次开始执行时间
	private Long lastFinishTime = 0L;//最后一次执行完成时间
	private int state = 1;			//任务状态0禁用、1启动、2删除
	private int deply = 0;			//延时启动，默认为0，表示不延时启动
	
	public Task(String taskId){
		this.id = taskId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getCronDesc() {
		return cronDesc;
	}
	public void setCronDesc(String cronDesc) {
		this.cronDesc = cronDesc;
	}
//	public List<ScheduleJob> getChildren() {
//		return children;
//	}
//	public void setChildren(List<ScheduleJob> children) {
//		this.children = children;
//	}
	public int getExecute() {
		return execute;
	}
	public void setExecute(int execute) {
		this.execute = execute;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Long getLastExeTime() {
		return lastExeTime;
	}
	public void setLastExeTime(Long lastExeTime) {
		this.lastExeTime = lastExeTime;
	}
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	public String getTriggerDesc() {
		return triggerDesc;
	}
	public void setTriggerDesc(String triggerDesc) {
		this.triggerDesc = triggerDesc;
	}
	public int getDeply() {
		return deply;
	}
	public void setDeply(int deply) {
		this.deply = deply;
	}
	public int getPlanExe() {
		return planExe;
	}
	public void setPlanExe(int planExe) {
		this.planExe = planExe;
	}
//	public String getTriggerGroup() {
//		return triggerGroup;
//	}
//	public void setTriggerGroup(String triggerGroup) {
//		this.triggerGroup = triggerGroup;
//	}
//	public String getTriggerGroupDesc() {
//		return triggerGroupDesc;
//	}
//	public void setTriggerGroupDesc(String triggerGroupDesc) {
//		this.triggerGroupDesc = triggerGroupDesc;
//	}
	public Long getLastFinishTime() {
		return lastFinishTime;
	}

	public void setLastFinishTime(Long lastFinishTime) {
		this.lastFinishTime = lastFinishTime;
	}
	
}
