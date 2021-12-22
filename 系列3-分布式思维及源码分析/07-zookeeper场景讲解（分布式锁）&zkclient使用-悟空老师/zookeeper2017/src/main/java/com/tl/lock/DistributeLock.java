package com.tl.lock;/*
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

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/***
 * X锁 独占锁
 */
public class DistributeLock  {

    private ZooKeeper zooKeeper;
    private String path;
    private CountDownLatch latch;
    public DistributeLock(String host, String path) {
        try {
            this.zooKeeper = new ZooKeeper(host, 6000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {

            }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.path = path;
    }

    /***
     * 创建一个临时的节点
     */
    public void lock(){
        try {
            zooKeeper.create(path,path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            return; //拿到锁了
        } catch (Exception e) {
            try {
                latch=new CountDownLatch(1);
                latch.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            lock();

        }

    }

    public void unlock(){
        try {
            //删除这个节点
            zooKeeper.delete(path,-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

}
