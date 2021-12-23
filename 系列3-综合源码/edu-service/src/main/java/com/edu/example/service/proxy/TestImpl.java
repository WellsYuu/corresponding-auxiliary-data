/**
 * 
 */
package com.edu.example.service.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 被代理的真实对象
 * @author 张飞老师
 */
public class TestImpl implements Test {
	@Override
	public void test() {
		System.out.println("=======test==========");
		this.test1();
	}

	@Override
	public void test1() {
		System.out.println("=======test1==========");
	}
	
	@Override
	public void abc() {
		System.out.println("=======abac==========");
	}

}
