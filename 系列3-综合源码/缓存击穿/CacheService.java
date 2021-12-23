/**
 * 
 */
package com.edu.example.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.example.bean.Order;
import com.edu.example.dao.OrderMapper;

/**
 * 缓存service
 * @author 张飞老师
 */
@Component
public class CacheService {
	private static Logger logger = LoggerFactory.getLogger(CacheService.class);
	@Autowired
	private OrderMapper orderMapper;
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private CacheTemplateService cacheTemplateService;
	
	private static final String CACHE_KEY = "zhangfei";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public /*synchronized*/ List<Order> queryOrderAmount(){
		ValueOperations opsForValue = redisTemplate.opsForValue();
		
		String json = String.valueOf(opsForValue.get(CACHE_KEY));
		if(StringUtils.isNotEmpty(json) && !json.equalsIgnoreCase("null")){
			logger.info("========query cache=====");
			return JSON.parseArray(json, Order.class);
		}
		
		synchronized (this) {
			json = String.valueOf(opsForValue.get(CACHE_KEY));
			if(StringUtils.isNotEmpty(json) && !json.equalsIgnoreCase("null")){
				logger.info("========query cache=====");
				return JSON.parseArray(json, Order.class);
			}
			
			// 2~3s
			List<Order> list = orderMapper.getAll();
			opsForValue.set(CACHE_KEY, JSON.toJSON(list), 10, TimeUnit.MINUTES);
			return list;
		}
	}
	
	public List<Order> queryTemplate(){
		return cacheTemplateService.findCache(CACHE_KEY, 10, TimeUnit.MINUTES, 
				new TypeReference<List<Order>>(){}, 
				new CacheLoadable<List<Order>>() {
			@Override
			public List<Order> load() {
				return orderMapper.getAll();
			}
		});
	}
}
