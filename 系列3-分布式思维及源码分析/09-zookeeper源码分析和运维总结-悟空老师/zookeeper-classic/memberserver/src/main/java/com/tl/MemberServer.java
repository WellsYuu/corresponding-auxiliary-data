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

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.*;

import java.io.IOException;

public class MemberServer  implements Watcher {
  ZookeeperRegister zookeeperRegister=new ZookeeperRegister();
  private ZooKeeper zooKeeper;

  /***
   * 服务进行注册
   * 定义一个父节点为tl
   * 在TL下可以注册多个服务名称 模拟memberServer
   * @param serverInfo
   */
  void register(String serverInfo){
    zooKeeper=zookeeperRegister.getConnection(this);
    try {
      String path=zooKeeper.create(ZookeeperRegister.ROOT+"/server",serverInfo.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
      System.out.println("创建节点成功"+path);
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


  }



  public void process(WatchedEvent event) {

  }

  public static void main(String[] args) throws IOException {
    MemberServer memberServer=new MemberServer();
    TlState tlState=new TlState();
    tlState.setClientName(null);
    tlState.setIp("192.168.0.101");
    tlState.setPort("80");
    tlState.setStatus("init");
    tlState.setServerName("会员系统");
    memberServer.register(JSON.toJSONString(tlState));
    System.out.println("我是服务端");
    System.in.read();
  }
}
