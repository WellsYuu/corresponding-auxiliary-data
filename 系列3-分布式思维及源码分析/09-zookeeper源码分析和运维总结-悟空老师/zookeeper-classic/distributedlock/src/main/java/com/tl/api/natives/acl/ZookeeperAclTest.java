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
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperAclTest {
    final static Logger logger = LoggerFactory.getLogger(ZookeeperAclTest.class);
    public static void main(String[] args) throws Exception {

        ZookeeperAcl zookeeperAcl=new ZookeeperAcl(new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });
        String path="/wukong";
        zookeeperAcl.addPersistent2(path,"猴子");
      /*  System.out.println("--------------------"+zookeeperAcl.getData(path));
         zookeeperAcl.setData(path,"abc");//如果用addPersistent3的话 不能设置 因为只读
        System.out.println("--------------------");*/
        try {
            //不用授权
            ZooKeeper zk1 = new ZooKeeper(ZookeeperUtil.connectString, ZookeeperUtil.sessionTimeout, null);
            Thread.sleep(2000);
            logger.info("不用授权:"+ zk1.getData(path, false, null));
            zk1.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        try {
            //用授权
            System.out.println( zookeeperAcl.getData(path));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        try {
            //用错误的授权
            ZooKeeper zk1 = new ZooKeeper(ZookeeperUtil.connectString, ZookeeperUtil.sessionTimeout, null);
            zk1.addAuthInfo("digest","123".getBytes());
            Thread.sleep(2000);
            logger.info("用错误的授权:"+ zk1.getData(path, false, null));
            zk1.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            //用正确的授权
            ZooKeeper zk1 = new ZooKeeper(ZookeeperUtil.connectString, ZookeeperUtil.sessionTimeout, null);
            zk1.addAuthInfo("digest","111".getBytes());
            Thread.sleep(2000);
            logger.info("用正确的授权："+ zk1.getData(path, false, null));
            zk1.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        zookeeperAcl.delete(path);
        zookeeperAcl.close();
    }
}
