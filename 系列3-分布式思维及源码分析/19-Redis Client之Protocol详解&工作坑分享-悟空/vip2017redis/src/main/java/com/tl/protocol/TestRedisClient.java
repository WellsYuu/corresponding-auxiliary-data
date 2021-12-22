package com.tl.protocol;/*
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
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestRedisClient {
    public static void main(String[] args) throws IOException {

 /*       RedisClient redisClient=new RedisClient();
        System.out.println(redisClient.set("wk","悟空"));
        System.out.println(redisClient.get("wk"));*/


        List<RedisClient> pool=new ArrayList<>();
        pool.add(new RedisClient("192.168.0.12",6379));
        pool.add(new RedisClient("192.168.0.12",6380));
        pool.add(new RedisClient("192.168.0.12",6381));
        Crc16Sharding crc16Sharding=new Crc16Sharding(pool);
        for (int i=0;i<100;i++){
            String key="xx"+i;

            RedisClient redisClient=crc16Sharding.crc16(key);
            redisClient.set(key,i+"");
            System.out.println(redisClient.get(key));;


        }




    }
}

