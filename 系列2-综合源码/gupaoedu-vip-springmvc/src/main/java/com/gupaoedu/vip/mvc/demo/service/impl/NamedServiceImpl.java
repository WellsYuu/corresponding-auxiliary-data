package com.gupaoedu.vip.mvc.demo.service.impl;

import com.gupaoedu.vip.mvc.demo.service.INamedService;
import com.gupaoedu.vip.mvc.demo.service.IService;
import com.gupaoedu.vip.mvc.framework.annotation.GPAutowired;
import com.gupaoedu.vip.mvc.framework.annotation.GPService;

@GPService("myName")
public class NamedServiceImpl implements INamedService{

	@GPAutowired IService service;
	
}
