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

import java.util.ArrayList;
import java.util.List;

public class RedisTest {

    public static void main(String[] args) throws Exception {
        List<RedisClient2> pool=new ArrayList<RedisClient2>();
        pool.add(new RedisClient2("192.168.0.12",6379));
        pool.add(new RedisClient2("192.168.0.12",6380));
        pool.add(new RedisClient2("192.168.0.12",6381));
        Crc16Sharding  crc16Sharding=new Crc16Sharding(pool);
        for(int i=0;i<10;i++) {
            String key = "wk"+i;
            RedisClient2 redisClient2 = crc16Sharding.crc16hash(key);
            redisClient2.set(key,i+"");
            System.out.println(redisClient2);

        }



    }
}
