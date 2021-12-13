package com.gupaoedu.vip.aop.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	public void insert(){
		System.out.println("插入一个用户");
	}
	
}
