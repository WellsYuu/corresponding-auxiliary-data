package com.imooc.jiangzh;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonTest {

    public static void main(String[] args) {

        Config config = new Config();
        config.setCodec(new org.redisson.client.codec.StringCodec());
        config. useSingleServer().setAddress("192.168.1.18:19000");

        RedissonClient redisson = Redisson.create(config);
        RBucket<String> keyObject = redisson.getBucket("k2");

        keyObject.set("v2");

        System.out.println(keyObject.get());

        redisson.shutdown();
    }

}
