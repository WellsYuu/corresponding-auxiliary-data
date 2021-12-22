package com.tuling.springboot.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuling.springboot.SpringbootStart;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootStart.class)
public class SpringRedisTest {
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	@Test
	public void testRedis() throws Exception {
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		ops.set("name", "zhangsan");
		String value = ops.get("name");
		System.out.println(value);
		TestCase.assertEquals("zhangsan", value);
    }
}