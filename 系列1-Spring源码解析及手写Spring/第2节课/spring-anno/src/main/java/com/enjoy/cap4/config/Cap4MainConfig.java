package com.enjoy.cap4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.enjoy.cap1.Person;
import com.enjoy.cap2.controller.OrderController;

@Configuration
public class Cap4MainConfig {
	//��������ע��һ��bean, ����Ϊ����ֵ������, Ĭ���ǵ�ʵ��
	/*
	 * ������: ��Ҫ��Ե�ʵ��bean:Ĭ��������������ʱ�򴴽�����
	 * ������:��������ʱ�򲻴�������, ������һ��ʹ��(��ȡ)bean��ʱ��Ŵ�������ʼ��
	
	 */
	//@Lazy
	@Bean
	public Person person(){
		System.out.println("�����������person.......");
		return new Person("james",20);
	}
}
