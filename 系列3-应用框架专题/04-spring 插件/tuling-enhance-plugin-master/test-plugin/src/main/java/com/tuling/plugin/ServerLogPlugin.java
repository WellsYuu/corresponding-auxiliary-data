package com.tuling.plugin;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * 输出Bean方法执行日志
 * 
 * @author Tommy
 *
 */
public class ServerLogPlugin implements MethodBeforeAdvice {

	public void before(Method method, Object[] args, Object target) throws Throwable {
		String result = String.format("%s.%s() 参数:%s", method.getDeclaringClass().getName(), method.getName(),
				Arrays.toString(args));
		System.out.println(result);
	}

}
