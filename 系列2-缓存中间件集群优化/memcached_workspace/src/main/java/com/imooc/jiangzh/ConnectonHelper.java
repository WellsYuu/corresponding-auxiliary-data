package com.imooc.jiangzh;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class ConnectonHelper {

    public static MemcachedClient getClient(){
        // 连接配置
        MemcachedClientBuilder memcachedClientBuilder =
                new XMemcachedClientBuilder(AddrUtil.getAddresses("192.168.1.18:2222 192.168.1.18:6666"));
        // 创建与服务端之间的连接[ip地址，端口号，用户名和密码]
        // 获取操作业务对象
        MemcachedClient memcachedClient = null;
        try {
            memcachedClient = memcachedClientBuilder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return memcachedClient;
    }

    // 演示集群操作
    @Test
    public void cluTest() throws  Exception{

        MemcachedClient memcachedClient = ConnectonHelper.getClient();

//        String k1Value = memcachedClient.get("k1");
//        System.out.println("k1Value="+k1Value);
//
//        memcachedClient.set("v1",0,"Hello Jiangzh!!!");
//        String V1Value = memcachedClient.get("V1");
//        System.out.println("V1Value="+V1Value);


    }

//    public static void main(String[] args) throws Exception {
//
//        // 连接配置
//        // 创建与服务端之间的连接[ip地址，端口号，用户名和密码]
//        // 获取操作业务对象
//        MemcachedClient memcachedClient =
//                new XMemcachedClient("192.168.1.18",2222);
//
//        // 操作业务
//        String str = "Hello Imooc!";
//        boolean isSuccess = memcachedClient.set("k1", 3600, str);
//
//        String value = memcachedClient.get("k1");
//
//        System.out.println("value="+value);
//
//        // 关闭与服务端连接
//        memcachedClient.shutdown();
//
//    }

}
