package com.enjoy.king.tomcat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
* 控制类：定义为RESTFUL的控制类，对外开放http接口
* @author 【享学课堂】 King老师
* @throws Exception
*/
@RestController
public class Controller {

	/*
	* 对外开放的接口，地址为：http://127.0.0.1:8090/demo?param=123456
	* @param  param：参数
	* @return String
	* @author 【享学课堂】 King老师   架构技术QQ群：684504192
	* @throws Exception
	*/
	@RequestMapping("/demo")
	public String demo(@RequestParam(required = false) String param){
		try {
			//业务处理
			System.out.println("开始业务处理!－－－－－－");
			return "返回的参数是："+param;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
