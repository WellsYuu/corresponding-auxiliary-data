package com.gupaoedu.demo.mvc.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gupaoedu.demo.service.IModifyService;
import com.gupaoedu.demo.service.IQueryService;
import com.gupaoedu.mvcframework.annotation.GPAutowired;
import com.gupaoedu.mvcframework.annotation.GPController;
import com.gupaoedu.mvcframework.annotation.GPRequestMapping;
import com.gupaoedu.mvcframework.annotation.GPRequestParam;

@GPController
@GPRequestMapping("/web")
public class MyAction {
	
	@GPAutowired private IQueryService queryService;
	@GPAutowired("aa") private IModifyService modifyService;
	
	@GPRequestMapping("/search/*.json")
	public void search(HttpServletRequest request,HttpServletResponse response,
			@GPRequestParam("name") String name){
		String result = queryService.search(name);
		out(response,result);
	}
	
	
	@GPRequestMapping("/add.json")
	public void add(HttpServletRequest request,HttpServletResponse response,
			@GPRequestParam("name") String name,
			@GPRequestParam("addr") String addr){
		String result = modifyService.add(name,addr);
		out(response,result);
	}
	
	
	@GPRequestMapping("/remove.json")
	public void remove(HttpServletRequest request,HttpServletResponse response,
			@GPRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		out(response,result);
	}
	
	
	private void out(HttpServletResponse response,String str){
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
