package com.gupaoedu.vip.aop.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gupaoedu.vip.aop.service.MemberManagerService;

@ContextConfiguration(locations={"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberManagerServiceTest {
	
	@Autowired MemberManagerService memberManagerService;
	
	@Test
//	@Ignore
	public void testAdd(){
		memberManagerService.add(null);
	}
	
	
	//做事务代理的时候
	//TracationManage来管理事务操作（切面）
	//DataSource ，SessionFactory(DataSource)
    //DataSource 包含了连接信息，事物的提交或者回滚一些基础功能
	//通过连接点是可以获取到方法（切点）具体操作哪个DataSource
	//通过切面通知类型，去执行DataSource的功能方法
	
	//完全裸露，一丝不挂
	@Test
	@Ignore
	public void testRemove(){
		try {
			memberManagerService.remove(0);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	public void testModify(){
		memberManagerService.modify(null);
	}
	
	public void testQuery(){
		memberManagerService.query("");
	}
	
}
