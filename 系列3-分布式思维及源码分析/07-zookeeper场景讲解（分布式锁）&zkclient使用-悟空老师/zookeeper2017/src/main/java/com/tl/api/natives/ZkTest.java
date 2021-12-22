package com.tl.api.natives;/*
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

import com.tl.ZookeeperUtil;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/***
 * 表达两个意思
 * 1、watcher异步
 * 2、顺序颠倒 加入countdownlatch
 */
public class ZkTest  implements   Watcher{
    private ZooKeeper zooKeeper;
    private CountDownLatch latch=new CountDownLatch(1);
    public ZkTest(String host) {

        try {
            this.zooKeeper = new ZooKeeper(host, 6000,this);
            latch.await();
            System.out.println("我要输出这句话");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void process2 () {
       // latch.countDown();
        System.out.println("---process2----");
    }

    @Override
    public void process(WatchedEvent event) {
        //需要改数据状态
        if(event.getState()== Event.KeeperState.SyncConnected) {
            latch.countDown();
        }

    }

    public static void main(String[] args) {
        ZkTest zkTest=new ZkTest(ZookeeperUtil.connectString);
        zkTest.process2();
    }


}
