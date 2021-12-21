package com.test.server.test;/**
 * Created by Administrator on 2018/6/13.
 */

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/13
 **/
public class RedisTest {
    private JedisPool jedispool;

    @Before
    public void init() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-redis.xml");
        context.start();
        jedispool = context.getBean(JedisPool.class);
    }

    @Test
    public void commonTest() throws InterruptedException {
        Jedis jedis = jedispool.getResource();
        jedis.set("test", "hello luban");
        System.out.println(jedis.get("test"));
        jedis.del("test");
    }
}
