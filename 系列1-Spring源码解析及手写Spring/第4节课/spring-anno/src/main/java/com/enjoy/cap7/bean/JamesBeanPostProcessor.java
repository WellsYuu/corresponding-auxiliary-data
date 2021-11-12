package com.enjoy.cap7.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
@Component
public class JamesBeanPostProcessor implements BeanPostProcessor{
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		//返回一个的对象(传过来的对象)
		//在初始化方法调用之前进行后置处理工作,
		//什么时候调用它: init-method=init之前调用
		System.out.println("postProcessBeforeInitialization...."+beanName+"..."+bean);
		return bean;
	}
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessAfterInitialization...."+beanName+"..."+bean);
		return bean;
	}
}
