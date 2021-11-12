package com.enjoy.cap5.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WinCondition implements Condition{

	
	
	/*
	*ConditionContext: �ж���������ʹ�õ�������(����)
	*AnnotatedTypeMetadata: ע�����Ϣ
	*
	*/
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// TODO �Ƿ�ΪWINDOWϵͳ
		//�ܻ�ȡ��IOC��������ʹ�õ�beanFactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//��ȡ��ǰ��������(�������ǲ���ϵͳ��WIN����LINUX??)
		Environment environment = context.getEnvironment();
		String os_name = environment.getProperty("os.name");
		if(os_name.contains("Windows")){
			return true;
		}
		return false;
	}

}
