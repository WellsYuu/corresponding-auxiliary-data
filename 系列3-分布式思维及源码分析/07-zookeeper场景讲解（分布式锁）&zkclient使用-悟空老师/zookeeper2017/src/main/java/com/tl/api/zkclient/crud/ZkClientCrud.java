package com.tl.api.zkclient.crud;/*
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
import org.I0Itec.zkclient.ZkClient;

public class ZkClientCrud {
    ZkClient zkClient;

    public static void main(String[] args) {
        ZkClient zkClient=new ZkClient(ZookeeperUtil.connectString,ZookeeperUtil.sessionTimeout);
        //面试说 可以递归创建节点 但是都是null空节点
        zkClient.createPersistent("/abc/ccc/ddd",true);

       // zkClient.createPersistent("/abc/ccc/ddd",true);
    }



}
