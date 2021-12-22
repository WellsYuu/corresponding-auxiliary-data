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
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

public class OrderClient implements Watcher
{

    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    ZookeeperRegister zookeeperRegister=new ZookeeperRegister();

    /***
     * 订阅tl的节点下面的子节点
     * 如果刚启动起来的 就去注册
     * @param clientName
     */
    public void subscribe(String clientName){
        ZooKeeper zooKeeper= zookeeperRegister.getConnection(this);
        try {
            List<String>  list=zooKeeper.getChildren(ZookeeperRegister.ROOT,true);
            if(list.isEmpty()){
                throw new RuntimeException("没有会员系统服务");
            }
            System.out.println(list);
            for(String node:list){
                System.out.println(node);
                byte[]  data=zooKeeper.getData(ZookeeperRegister.ROOT+"/"+node,true,null);
                TlState tlState= JSON.parseObject(new String(data),TlState.class);
                if(null!=tlState&&tlState.getStatus().equals("init")){
                    //就是建立关系 服务端和客户端的一个关系
                    tlState.setClientName(clientName);
                    tlState.setStatus("run");
                    tlState.setNode(node);
                    zooKeeper.setData(ZookeeperRegister.ROOT+"/"+tlState.getNode(),JSON.toJSONString(tlState).getBytes(),-1);
                }else{
                    System.out.println("没有找到可用run服务");
                }

            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        OrderClient orderClient=new OrderClient();
        orderClient.subscribe("交易系统01");
        orderClient.setClientName("交易系统01");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /***
     * 这感应就是节点发生了变化 说明member服务不可用或者少了/多了 需要重新去订阅
     * @param event
     */
    public void process(WatchedEvent event) {
       Event.EventType eventType= event.getType();
      if(eventType==eventType.NodeChildrenChanged){
          subscribe( this.getClientName());

      }




    }
}
