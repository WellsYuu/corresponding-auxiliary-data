package com.tl.service;

import com.tl.service.impl.UsersService;

/**
 * 服务提供者就是委托人
 * @author Administrator
 *
 */
public class UsersServiceImpl implements UsersService {
	/* (non-Javadoc)
	 * @see com.tl.service.impl.UsersService#add()
	 */
	@Override
	public void add(){
		System.out.println("添加用户");
	}
	
	/* (non-Javadoc)
	 * @see com.tl.service.impl.UsersService#update()
	 */
	@Override
	public void update(){
		System.out.println("修改用户");
	}
	
	
}
