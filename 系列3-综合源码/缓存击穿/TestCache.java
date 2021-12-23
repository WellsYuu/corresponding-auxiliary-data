/**
 * 
 */
package com.edu.example.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.edu.example.bean.Order;
import com.edu.example.service.impl.CacheService;

/**
 * @author 张飞老师
 */
public class TestCache extends TestSupport{

	private static Logger logger = LoggerFactory.getLogger(TestCache.class);
	@Autowired
	private CacheService cacheService;
	private static final String CACHE_KEY = "zhangfei";
	private CountDownLatch latch = new CountDownLatch(10);
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Before
	public void init(){
		logger.info("=======init=====");
		redisTemplate.delete(CACHE_KEY);
	}
	
	@Test
	public void testCache(){
		logger.info("=======test1=====");
		List<Order> list1 = cacheService.queryOrderAmount();
		logger.info(list1.toString());
		
		logger.info("=======test2=====");
		List<Order> list2 = cacheService.queryOrderAmount();
		logger.info(list2.toString());
	}
	
	@Test
	public void test1() throws Exception{
		// 开启10个子线程 模拟并发执行查询任务
		for(int i = 0;i<10 ; i++){
			new Thread(new QueryTask(),"线程"+(i+1)).start();
			latch.countDown();
		}
		// 主线程暂停5s，等待子线程完成
		Thread.currentThread().sleep(5000);
	}
	
	private class QueryTask implements Runnable {
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			List<Order> list1 = cacheService.queryOrderAmount();
			List<Order> list1 = cacheService.queryTemplate();
			logger.info(list1.toString());
		}
	}
}
