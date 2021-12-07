package com.imooc.jiangzh;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {

    public static void main(String[] args) {
        // 开启Jedis连接池
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxTotal(50);
        jpc.setMinIdle(10);
        JedisPool jp = new JedisPool(jpc,"192.168.1.18",19000);

        // 开启Jedis客户端
        Jedis jedis = jp.getResource();

        jedis.set("k1","v1");
        System.out.println(jedis.get("k1"));

        jedis.close();
    }


}
