/**
 * 
 */
package com.edu.example.service.impl;

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
import com.edu.example.dao.OrderMapper;

/**
 * @author 张飞老师
 */
@Component
public class CacheTemplateService {
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private OrderMapper orderMapper;
	private static Logger logger = LoggerFactory.getLogger(CacheService.class);
	/**
	 * 避免缓存穿透的模板
	 * 读取缓存的模板操作
	 * @param key 缓存的key
	 * @param expire 缓存的失效时间
	 * @param unit 失效时间单位
	 * @param clazz 缓存的类型
	 * @param cacheLoadable 如果缓存失效,怎么获取
	 * @return
	 */
	public <T> T findCache(String key ,  long expire, TimeUnit unit , 
			TypeReference<T> clazz , CacheLoadable<T> cacheLoadable){
		ValueOperations opsForValue = redisTemplate.opsForValue();
		String json = String.valueOf(opsForValue.get(key));
		if(StringUtils.isNotEmpty(json) && !json.equalsIgnoreCase("null")){
			logger.info("========query cache=====");
			return JSON.parseObject(json, clazz);
		}else{
			synchronized(this){
				json = String.valueOf(opsForValue.get(key));
				if(StringUtils.isNotEmpty(json) && !json.equalsIgnoreCase("null")){
					logger.info("========query cache=====");
					return JSON.parseObject(json, clazz);
				}
				T result = cacheLoadable.load();
				// 2~3s
				opsForValue.set(key, JSON.toJSON(result), expire, unit);
				return result;
			}
		}
	}
}
