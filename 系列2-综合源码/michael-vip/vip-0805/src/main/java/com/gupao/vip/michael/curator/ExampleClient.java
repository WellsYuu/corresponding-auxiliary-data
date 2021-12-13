package com.gupao.vip.michael.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable{

    private final String name;
    private final LeaderSelector leaderSelector;
    private final AtomicInteger leaderCount=new AtomicInteger();

    public ExampleClient(CuratorFramework client,String path,String name) {
        this.name = name;
        this.leaderSelector = new LeaderSelector(client,path,this);
        leaderSelector.autoRequeue(); //自动抢
    }

    public void start(){
        leaderSelector.start();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        final int waitSeconds=new Random().nextInt(50);
        System.out.println(name+"->我现在是leader，等待时间："+waitSeconds+", 抢到领导的次数:"+leaderCount.getAndIncrement());

        TimeUnit.SECONDS.toMillis(1000);
    }
}
