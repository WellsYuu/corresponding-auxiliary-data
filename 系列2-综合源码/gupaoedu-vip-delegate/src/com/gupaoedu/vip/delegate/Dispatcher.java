package com.gupaoedu.vip.delegate;

public class Dispatcher implements IExector{
	IExector exector;
	
	Dispatcher(IExector exector){
		this.exector = exector;
	}
	
	
	//项目经理，虽然也有执行方法
	//但是他的工作职责是不一样的
	public void doing() {
		this.exector.doing();
	}

}
