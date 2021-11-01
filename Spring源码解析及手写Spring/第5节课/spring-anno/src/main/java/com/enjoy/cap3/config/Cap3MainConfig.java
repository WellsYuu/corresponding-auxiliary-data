package com.enjoy.cap3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.enjoy.cap1.Person;
import com.enjoy.cap2.controller.OrderController;

@Configuration
public class Cap3MainConfig {
	//给容器中注册一个bean, 类型为返回值的类型, 默认是单实例
	/*
	 * prototype:多实例: IOC容器启动的时候,IOC容器启动并不会去调用方法创建对象, 而是每次获取的时候才会调用方法创建对象
	 * singleton:单实例(默认):IOC容器启动的时候会调用方法创建对象并放到IOC容器中,以后每次获取的就是直接从容器中拿(大Map.get)的同一个bean
	 * request: 主要针对web应用, 递交一次请求创建一个实例
	 * session:同一个session创建一个实例
	 */
	//@Scope("prototype")
	@Bean
	public Person person(){
		return new Person("james",20);
	}
}
