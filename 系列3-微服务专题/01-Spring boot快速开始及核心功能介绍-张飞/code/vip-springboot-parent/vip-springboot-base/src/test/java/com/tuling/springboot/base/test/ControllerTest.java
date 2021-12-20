package com.tuling.springboot.base.test;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tuling.springboot.base.SampleController;

@Ignore
@SpringBootTest(classes=SampleController.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {
	
	@Autowired
	private SampleController controller ;
	
//	@Test
	public void testHome(){
		TestCase.assertEquals("Hello World!", controller.home());
		System.out.println(controller.home());
	}

}
