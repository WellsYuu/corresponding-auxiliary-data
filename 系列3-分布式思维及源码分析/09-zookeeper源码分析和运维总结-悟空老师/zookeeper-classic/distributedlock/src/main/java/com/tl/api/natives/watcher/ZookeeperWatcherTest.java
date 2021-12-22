package com.tl.api.natives.watcher;
/*
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

public class ZookeeperWatcherTest {

    public static void main(String[] args) throws Exception {
        String root = "/root";

        //建立watcher
        ZooKeeperWatcher zkWatch = new ZooKeeperWatcher();

       // Thread.sleep(1000);

        // 清理节点
       // zkWatch.deleteRecursive(root);

        if (null != zkWatch.addPersistent(root, System.currentTimeMillis() + "")) {
            Thread.sleep(1000);
            // 读取数据
            zkWatch.getData(root, true);
            // 读取子节点
            zkWatch.getChildren(root, true);//设置为false就不会监听了
            // 更新数据
            zkWatch.setData(root, System.currentTimeMillis() + "");
           Thread.sleep(1000);
            // 创建子节点
            zkWatch.addPersistent(root + "/c", System.currentTimeMillis() + "");
            Thread.sleep(1000);
            zkWatch.setData(root + "/c", System.currentTimeMillis() + "");
            zkWatch.deleteRecursive(root);

        }

    }
}
