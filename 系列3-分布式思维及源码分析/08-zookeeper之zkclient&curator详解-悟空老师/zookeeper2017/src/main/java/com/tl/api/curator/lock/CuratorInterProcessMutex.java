package com.tl.api.curator.lock;/*
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
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorInterProcessMutex {
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000, 5);//重试策略
    //工厂创建连接
    CuratorFramework   cf= CuratorFrameworkFactory.builder()
            .connectString(ZookeeperUtil.connectString)
            .sessionTimeoutMs(ZookeeperUtil.sessionTimeout)
            .retryPolicy(retryPolicy)
            .build();

     InterProcessMutex lock;
    public CuratorInterProcessMutex(String path) {
        lock=new InterProcessMutex(cf, path);
        cf.start();//链接
    }

    /***
     * 获取资源
     * @see org.apache.curator.framework.recipes.locks.LockInternals
     * @throws Exception
     */
    public void acquire() throws Exception {
        lock.acquire();
    }

    /****
     * 释放资源
     * @throws Exception
     */
    public void release() throws Exception {
        lock.release();
    }


}
