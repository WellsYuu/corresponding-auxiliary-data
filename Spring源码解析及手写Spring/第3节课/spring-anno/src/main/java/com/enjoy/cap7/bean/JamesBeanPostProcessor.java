package com.enjoy.cap7.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
@Component
public class JamesBeanPostProcessor implements BeanPostProcessor{
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		//����һ���Ķ���(�������Ķ���)
		//�ڳ�ʼ����������֮ǰ���к��ô�����,
		//ʲôʱ�������: init-method=init֮ǰ����
		System.out.println("postProcessBeforeInitialization...."+beanName+"..."+bean);
		return bean;
	}
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessAfterInitialization...."+beanName+"..."+bean);
		return bean;
	}
}
