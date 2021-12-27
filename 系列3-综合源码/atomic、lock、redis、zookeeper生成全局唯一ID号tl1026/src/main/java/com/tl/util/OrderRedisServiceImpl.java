package com.tl.util;/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * 以往视频加小乔老师QQ：895900009
 * 悟空老师QQ：245553999
 */

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderRedisServiceImpl implements  OrderServer {
    static JedisPool jedisPool;
    static{
        jedisPool=new JedisPool(new JedisPoolConfig(),"192.168.0.102",6379,3000,"bit");
    }

    public  String getOrderNo(){
        SimpleDateFormat simpleDateFormat=  new SimpleDateFormat("YYYYmmDDHHMMSS");
        return simpleDateFormat.format(new Date())+jedisPool.getResource().incr("order_key");

    }
}
