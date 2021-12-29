package com.tl.service;


/**
 * 服务提供者就是委托人
 * 测试使用CGLIB来实现代理
 * @author Administrator
 *
 */
public class DeptServiceImpl{
	public void add(){
		System.out.println("添加部门");
	}
	
	public void update(){
		System.out.println("修改部门");
	}
	
	
}
