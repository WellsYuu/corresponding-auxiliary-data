package com.gupao.vip.michael.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MasterSelector {

    private final static String CONNECTSTRING="192.168.11.129:2181,192.168.11.134:2181," +
            "192.168.11.135:2181,192.168.11.136:2181";

    private final static String MASTER_PATH="/curator_master_path";
    public static void main(String[] args) {

        CuratorFramework curatorFramework= CuratorFrameworkFactory.builder().connectString(CONNECTSTRING).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).build();

        LeaderSelector leaderSelector=new LeaderSelector(curatorFramework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("获得leader成功");
                TimeUnit.SECONDS.sleep(2);
            }
        });
        leaderSelector.autoRequeue();
        leaderSelector.start();//开始选举
    }
}
