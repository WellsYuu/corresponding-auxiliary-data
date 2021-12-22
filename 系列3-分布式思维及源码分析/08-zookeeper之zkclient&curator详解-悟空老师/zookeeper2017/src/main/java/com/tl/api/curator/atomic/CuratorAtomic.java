package com.tl.api.curator.atomic;/*
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
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;

public class CuratorAtomic {

    RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000, 5);//重试策略
    //工厂创建连接
 /*   CuratorFramework cf= CuratorFrameworkFactory.builder()
            .connectString(ZookeeperUtil.connectString)
            .sessionTimeoutMs(ZookeeperUtil.sessionTimeout)
            .retryPolicy(retryPolicy)
            .build();*/
    CuratorFramework cf=CuratorFrameworkFactory.newClient(ZookeeperUtil.connectString,new RetryOneTime(1));
    DistributedAtomicInteger distributedAtomicInteger=null;
    CuratorAtomic(String path){
        distributedAtomicInteger=new DistributedAtomicInteger(cf,path,retryPolicy);
        cf.start();//链接
    }

    public  void increment(){
        try {
            distributedAtomicInteger.increment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  AtomicValue<Integer>  get(){
        try {
            return distributedAtomicInteger.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
