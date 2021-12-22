package com.tl.api.curator.wacher;/*
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
import com.tl.api.curator.CuratorCrud;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorWatcherTest {

    public static void main(String[] args) throws Exception {
        String path = "/root";
        /****============监听开启========**/
        CuratorWatcher curatorWatcher = new CuratorWatcher();
       // curatorWatcher.setListenter1(path);
        //curatorWatcher.setListenter2(path);
        curatorWatcher.setListenter3(path);//子节点重复监听
       // curatorWatcher.setListenter4(path);//当前节点重复监听
       // curatorWatcher.setListenter5(path);//当前和子节点重复监听
        /****============数据更新========**/
        CuratorCrud curatorCrud=new CuratorCrud();
        if(null!=curatorCrud.checkExists(path)) {
            curatorCrud.delete(path);
        }
        Thread.sleep(1000);
        curatorCrud.create(path,"111".getBytes());
        System.out.println("------创建目录-----");
        Thread.sleep(1000);
        curatorCrud.setData(path,"222".getBytes());
        System.out.println("------设置目录-----");
        Thread.sleep(1000);
        curatorCrud.create(path+"/ccc","333".getBytes());
        System.out.println("------创建子目录-----");
        Thread.sleep(1000);
        curatorCrud.setData(path+"/ccc","444".getBytes());
        System.out.println("------设置子目录-----");
        Thread.sleep(1000);

    }

}
