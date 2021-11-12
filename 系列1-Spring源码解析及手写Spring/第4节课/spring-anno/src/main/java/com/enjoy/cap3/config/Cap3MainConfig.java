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
	//��������ע��һ��bean, ����Ϊ����ֵ������, Ĭ���ǵ�ʵ��
	/*
	 * prototype:��ʵ��: IOC����������ʱ��,IOC��������������ȥ���÷�����������, ����ÿ�λ�ȡ��ʱ��Ż���÷�����������
	 * singleton:��ʵ��(Ĭ��):IOC����������ʱ�����÷����������󲢷ŵ�IOC������,�Ժ�ÿ�λ�ȡ�ľ���ֱ�Ӵ���������(��Map.get)��ͬһ��bean
	 * request: ��Ҫ���webӦ��, �ݽ�һ�����󴴽�һ��ʵ��
	 * session:ͬһ��session����һ��ʵ��
	 */
	//@Scope("prototype")
	@Bean
	public Person person(){
		return new Person("james",20);
	}
}
