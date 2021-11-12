package com.xiangxue.ch8a.vo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：任务处理返回结果实体类
 */
public class TaskResult<R> {
	//方法本身运行是否正确的结果类型
	private final TaskResultType resultType;
	//方法的业务结果数据；
	private final R returnValue;
	//这里放方法失败的原因
	private final String reason;
	
	public TaskResult(TaskResultType resultType, R returnValue, String reason) {
		super();
		this.resultType = resultType;
		this.returnValue = returnValue;
		this.reason = reason;
	}
	
	//方便业务人员使用，这个构造方法表示业务方法执行成功返回的结果
	public TaskResult(TaskResultType resultType, R returnValue) {
		super();
		this.resultType = resultType;
		this.returnValue = returnValue;
		this.reason = "Success";
	}

	public TaskResultType getResultType() {
		return resultType;
	}

	public R getReturnValue() {
		return returnValue;
	}

	public String getReason() {
		return reason;
	}

	@Override
	public String toString() {
		return "TaskResult [resultType=" + resultType 
				+ ", returnValue=" + returnValue 
				+ ", reason=" + reason + "]";
	}	
	
	
	
}
