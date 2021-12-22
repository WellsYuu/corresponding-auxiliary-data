package com.tl.api.natives.crud;/*
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
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperClient {
  private ZooKeeper zk;
    final static Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);//写不写无所谓
    public ZookeeperClient(Watcher watcher,CountDownLatch latch) throws Exception {
         zk = new ZooKeeper(ZookeeperUtil.connectString, ZookeeperUtil.sessionTimeout, watcher);

    }

    public ZookeeperClient() throws Exception {
         zk = new ZooKeeper(ZookeeperUtil.connectString, ZookeeperUtil.sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("--------");
            }
        });

    }

    /***
     * 创建持久类型
     * @param path
     * @param data
     * @return
     * @see ZooKeeperMain
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String addPersistent(String path,String data) throws KeeperException, InterruptedException {
        return  zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    public String addPersistent(String path,byte[] data) throws KeeperException, InterruptedException {
        return  zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }


    /***
     * 更新信息
     * @param path
     * @see ZooKeeperMain#processZKCmd
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getData(String path) throws KeeperException, InterruptedException {
        byte data[] = zk.getData(path,false,null);
        data = (data == null)? "null".getBytes() : data;
        return new String(data);

    }

    public byte[] getData2(String path) throws KeeperException, InterruptedException {
        return  zk.getData(path,false,null);


    }


    /***
     * 更新信息
     * @param path
     * @param data
     * @see ZooKeeperMain#processZKCmd
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat setData(String path,String data) throws KeeperException, InterruptedException {
        return zk.setData(path, data.getBytes(), -1);
        //byte[] data = zk.getData("path", false, null);
        //System.out.println(new String(data));
    }

    /***
     * 是否存在
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat exists(String path) throws KeeperException, InterruptedException {
        return zk.exists(path,false);

    }

    public  List<String> getChildren(String path,Watcher watcher) throws KeeperException, InterruptedException {
        return zk.getChildren(path,watcher);
    }

    /***
     * 删除
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void delete(String path) throws KeeperException, InterruptedException {
         zk.delete(path,-1);
    }
    /***
     * 删除
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void deleteRecursive(String path) throws KeeperException, InterruptedException {
        ZKUtil.deleteRecursive(zk, path);
    }



    public void close() throws InterruptedException {

        zk.close();
    }

}
