package com.edu.example.test;


import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.edu.example.bean.User;
import com.edu.example.service.UserService;

public class TestDemo extends TestSupport{
	private static Logger logger = LogManager.getLogger(TestDemo.class);
	@Resource
	private UserService userService;
	
	@Test
	public void test01(){
		User u = new User();
		u.setName("ZhangFei");
		u.setAddress("长沙市岳麓区....");
		this.userService.insertUser(u);
	}
}
