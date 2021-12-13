package com.gupao.vip.michael.javaapilock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 *
 * 创建会话
 */
public class ZookeeperClient {

    private final static String CONNECTSTRING="192.168.11.129:2181,192.168.11.134:2181," +
            "192.168.11.135:2181,192.168.11.136:2181";

    private static int sessionTimeout=5000;

    //获取连接
    public static ZooKeeper getInstance() throws IOException, InterruptedException {
        final CountDownLatch conectStatus=new CountDownLatch(1);
        ZooKeeper zooKeeper=new ZooKeeper(CONNECTSTRING, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                if(event.getState()== Event.KeeperState.SyncConnected){
                    conectStatus.countDown();
                }
            }
        });
        conectStatus.await();
        return zooKeeper;
    }

    public static int getSessionTimeout() {
        return sessionTimeout;
    }
}
