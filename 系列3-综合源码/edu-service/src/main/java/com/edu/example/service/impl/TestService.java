/**
 * 
 */
package com.edu.example.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.example.bean.Order;
import com.edu.example.dao.OrderMapper;

/**
 * @author 张飞老师
 */
@Service
public class TestService {
	private static Logger logger = LoggerFactory.getLogger(TestService.class);
	@Resource
	private OrderMapper orderMapper;
	// spring 应用上下文
	@Autowired
	private ApplicationContext context;//
	
	private TestService proxy;

	@PostConstruct
	public void init(){
		// 从spring上下文获取aop代理对象
		proxy = context.getBean(TestService.class);
	}
	
	/**
	 * 场景：
	 * 1.parent业务执行之前，需要调用child
	 * 2.child方法不是很重要，也就是说child执行成功还是失败对parent不产生任何影响
	 * 
	 * 预期结果：
	 * child回滚
	 * parent成功
	 * 
	 * 实际结果：
	 * 都成功了
	 * 
	 * 根本原因：
	 * jdk 动态代理
	 * 
	 * 解决方案：
	 * 暴露AopProxy到当前线程中
	 * 所以应该要从AopProxy上下文获取AopProxy
	 * 
	 */
	@Transactional
	public void parent(){
		logger.debug("===============parent=============");
		logger.debug(this.getClass().getName());
		
		try {
//			child();
			// 解决方案：AopProxy上下文获取AopProxy
//			((TestService)(AopContext.currentProxy())).child();
			// 2.从spring上下文获取代理对象
			proxy.child();
		} catch (Exception e) {
			logger.error("parent 捕获到了 child产生的异常",e);
		}
		
		Order order = new Order();
		order.setTitle("张飞老师的订单Parent");
		order.setOrderNo("parent");
		order.setStatus(0);
		order.setAmount(100);
		
		orderMapper.insert(order);
	}
	
	/**
	 * REQUIRES_NEW ： 开启一个新事务，如果当前线程存在事务，那么会挂起当前事务，重新开启一个新的事务
	 * 新事务执行完毕之后，在唤醒之前的事务，继续执行
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void child(){
		logger.debug("===============child=============");
		Order order = new Order();
		order.setTitle("张飞老师的订单child");
		order.setOrderNo("child");
		order.setStatus(0);
		order.setAmount(100);
		
		orderMapper.insert(order);
		
		throw new RuntimeException();
	}
}
