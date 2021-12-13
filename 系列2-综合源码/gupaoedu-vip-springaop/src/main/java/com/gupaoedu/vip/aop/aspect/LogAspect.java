package com.gupaoedu.vip.aop.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

public class LogAspect {

	private final static Logger LOG = Logger.getLogger(LogAspect.class);
	
	public void before(JoinPoint joinPoint){
		LOG.info("调用方法之前执行" + joinPoint);
	}
	
	public void after(JoinPoint joinPoint){
		LOG.info("调用之后执行" + joinPoint);
	}
	
	public void afterReturn(JoinPoint joinPoint){
		LOG.info("调用获得返回值之后执行" + joinPoint);
	}
	
	
	public void afterThrow(JoinPoint joinPoint){
		LOG.info("抛出异常之后执行" + joinPoint);
	}
}
