package com.tl;/*
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

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZookeeperRegister {

  ZooKeeper zooKeeper;
  public static final String ROOT="/tl";


    public  ZooKeeper getConnection(Watcher watcher) {
        try {
            zooKeeper=new ZooKeeper("192.168.0.101:2181,192.168.0.102:2181,192.168.0.104:2181",6000,watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  zooKeeper;

    }



}
