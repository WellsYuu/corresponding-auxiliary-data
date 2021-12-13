package com.gupaoedu.vip.aop.aspect;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component//声明这个类是被SpringIOC容器来管理的，如果不声明，就无法做到自动织入
@Aspect//这个类被声明为是一个需要动态织入到我们的虚拟切面中的类
public class AnnotationAspect {

	private final static Logger LOG = Logger.getLogger(AnnotationAspect.class);
	//声明切点
	//因为要利用反射机制去读取这个切面中的所有的注解信息
	@Pointcut("execution(* com.gupaoedu.vip.aop.service..*(..))")
	public void pointcutConfig(){}
	
	@Before("pointcutConfig()")
	public void before(JoinPoint joinPoint){
		LOG.info("调用方法之前执行" + joinPoint);
	}
	
	
	@After("pointcutConfig()")
	public void after(JoinPoint joinPoint){
		LOG.info("调用之后执行" + joinPoint);
	}
	
	@AfterReturning(returning="returnValue",value="pointcutConfig()")
	public void afterReturn(JoinPoint joinPoint,Object returnValue){
		LOG.info("调用获得返回值" + returnValue);
	}
	
	
	@AfterThrowing("pointcutConfig()")
	public void afterThrow(JoinPoint joinPoint){
		
		
		
		System.out.println("切点的参数" + Arrays.toString(joinPoint.getArgs()));
		System.out.println("切点的方法" + joinPoint.getKind());
		System.out.println(joinPoint.getSignature());
		System.out.println(joinPoint.getTarget()); //生成以后的代理对象
		System.out.println(joinPoint.getThis());//当前类的本身(通过反射机制去掉用)
		
		LOG.info("抛出异常之后执行" + joinPoint);
	}
}
