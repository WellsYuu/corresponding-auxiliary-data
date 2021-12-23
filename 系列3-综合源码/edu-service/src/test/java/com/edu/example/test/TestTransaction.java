/**
 * 
 */
package com.edu.example.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.example.service.impl.TestService;

/**
 * @author 张飞老师
 */
public class TestTransaction extends TestSupport{
	private static Logger logger = LoggerFactory.getLogger(TestTransaction.class);
	@Resource
	private TestService testService;
	@Test
	public void testParent(){
		logger.debug("==================parent start===================");
		testService.parent();
		logger.debug("==================parent end===================");
	}
	
	@Test
	public void testChild(){
		logger.debug("==================child start===================");
		testService.child();
		logger.debug("==================child end===================");
	}
}
