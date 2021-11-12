package cn.enjoy.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


public class CuratorCreateSessionDemo {
    private final static String CONNECTSTRING="192.168.30.10:2181";
    public static void main(String[] args) {
        //创建会话的两种方式 normal
//        CuratorFramework curatorFramework= CuratorFrameworkFactory.
//                newClient(CONNECTSTRING,5000,5000,
//                        new ExponentialBackoffRetry(1000,3));
//        curatorFramework.start(); //start方法启动连接

//        //fluent风格
        CuratorFramework curatorFramework1=CuratorFrameworkFactory.builder().connectString(CONNECTSTRING).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).
                build();

        curatorFramework1.start();
        System.out.println("success");
    }
}
