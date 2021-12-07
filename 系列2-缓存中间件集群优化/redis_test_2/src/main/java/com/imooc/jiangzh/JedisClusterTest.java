package com.imooc.jiangzh;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JedisClusterTest {

    public static void main(String[] args) throws IOException {

        Set<HostAndPort> nodes = new HashSet<>();
        HostAndPort hap = new HostAndPort("192.168.1.18",6379);
        nodes.add(hap);

        JedisCluster jedisCluster =
                new JedisCluster(
                        nodes, 1000,
                        1000, 1,
                        new GenericObjectPoolConfig());


        jedisCluster.set("k1","v1");
        jedisCluster.get("k1");

        jedisCluster.close();

    }

}
