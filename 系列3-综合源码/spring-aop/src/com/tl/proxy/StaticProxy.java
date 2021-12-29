package com.tl.proxy;

import com.tl.service.impl.UsersService;

/**
 * 静态代理
 * @author Administrator
 *
 */
public class StaticProxy implements UsersService {
	
	private UsersService service;
	
	/**
	 * 代理和委托相绑定
	 * @param service
	 */
	public StaticProxy(UsersService service){
		this.service = service;
	}

	@Override
	public void add() {
		System.out.println("日志记录开始");
		//服务还是由委托类来提供
		service.add();
		System.out.println("日志记录结束");
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}	
