package com.gupao.vip.michael.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TreeCacheDemo {
    private final static String CONNECTSTRING="192.168.11.129:2181,192.168.11.134:2181," +
            "192.168.11.135:2181,192.168.11.136:2181";

    private static final String MASTER_PATH="/curator_master_path1";

    private static final int CLIENT_QTY=10; //客户端数量

    public static void main(String[] args) throws Exception {
        System.out.println("创建"+CLIENT_QTY+"个客户端，");
        List<CuratorFramework> clients = Lists.newArrayList();
        List<ExampleClient>     examples = Lists.newArrayList();
        try {
            for (int i = 0; i < CLIENT_QTY; i++) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECTSTRING,
                        new ExponentialBackoffRetry(1000, 3));
                clients.add(client);
                ExampleClient exampleClient = new ExampleClient(client, MASTER_PATH, "Client:" + i);
                examples.add(exampleClient);
                client.start();
                exampleClient.start();
            }
            System.in.read();
        }finally {
            for ( ExampleClient exampleClient : examples ){
                CloseableUtils.closeQuietly(exampleClient);
            }
            for ( CuratorFramework client : clients ){
                CloseableUtils.closeQuietly(client);
            }
        }
    }
}
