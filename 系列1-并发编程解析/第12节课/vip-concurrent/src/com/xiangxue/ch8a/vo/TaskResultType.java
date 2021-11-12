package com.xiangxue.ch8a.vo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：方法本身运行是否正确的结果类型
 */
public enum TaskResultType {
	//方法成功执行并返回了业务人员需要的结果
	Success,
	//方法成功执行但是返回的是业务人员不需要的结果
	Failure,
	//方法执行抛出了Exception
	Exception;
}
