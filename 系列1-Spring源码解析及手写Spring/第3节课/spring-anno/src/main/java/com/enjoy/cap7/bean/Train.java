package com.enjoy.cap7.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Train implements InitializingBean, DisposableBean{

	public Train(){
		System.out.println("Train......constructor............");
	}
	//������bean����ʱ,���ô˷���
	@Override
	public void destroy() throws Exception {
		System.out.println("Train......destory......");
		//logger.error
	}
	//�����ǵ�bean���Ը�ֵ�ͳ�ʼ�����ʱ����
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Train.......afterPropertiesSet()...");
		
	}

}
