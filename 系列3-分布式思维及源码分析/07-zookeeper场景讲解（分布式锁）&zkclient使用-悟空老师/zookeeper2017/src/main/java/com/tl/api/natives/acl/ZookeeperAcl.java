package com.tl.api.natives.acl;/*
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

import java.util.List;

/***
 * @see ZooDefs
 */
public class ZookeeperAcl {

    private ZooKeeper zk;
    /** 认证类型 */
    final static String scheme = "digest";
    final static String auth="111";
    public ZookeeperAcl(Watcher watcher) throws Exception {
        zk = new ZooKeeper(ZookeeperUtil.connectString, ZookeeperUtil.sessionTimeout, watcher);
        zk.addAuthInfo(scheme,auth.getBytes());
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

    /***
     * 创建持久类型
     * @param path
     * @param data
     * @return
     * @see ZooKeeperMain
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String addPersistent2(String path,String data) throws KeeperException, InterruptedException {
      //  List<ACL> CREATE_ONLY = Arrays.asList(new ACL[] { new ACL(ZooDefs.Perms.CREATE, ZooDefs.Ids.AUTH_IDS) });

        return  zk.create(path, data.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);

    }

    //READ_ACL_UNSAFE
    public String addPersistent3(String path,String data) throws KeeperException, InterruptedException {
    /*    List<ACL> acls = new ArrayList<ACL>(1);
        for (ACL ids_acl : ZooDefs.Ids.CREATOR_ALL_ACL) {
            acls.add(ids_acl);
        }*/

        return  zk.create(path, data.getBytes(), ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT);

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

    /***
     * 更新信息
     * @param path
     * @param data
     * @see ZooKeeperMain#processZKCmd
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat setData(String path, String data) throws KeeperException, InterruptedException {
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

    public List<String> getChildren(String path, Watcher watcher) throws KeeperException, InterruptedException {
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
