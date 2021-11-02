package com.xiangxue.ch8a.vo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：要求框架使用者实现的任务接口，因为任务的性质在调用时才知道，
 *所以传入的参数和方法的返回值均使用泛型
 */
public interface ITaskProcesser<T, R> {
	/**
	 * @param data 调用方法需要使用的业务数据
	 * @return 方法执行后业务方法的结果
	 */
	TaskResult<R> taskExecute(T data);
}
