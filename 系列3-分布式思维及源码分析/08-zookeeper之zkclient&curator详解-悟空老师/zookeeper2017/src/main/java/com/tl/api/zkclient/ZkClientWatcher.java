package com.tl.api.zkclient;/*
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
import org.I0Itec.zkclient.*;
import org.apache.zookeeper.Watcher;

import java.util.List;

public class ZkClientWatcher    {
    ZkClient zkClient;
    public ZkClientWatcher() {
        zkClient= new ZkClient(new ZkConnection(ZookeeperUtil.connectString), ZookeeperUtil.sessionTimeout);
    }


    public void createPersistent(String path,Object data){
        zkClient.createPersistent(path,data);
    }


    public  void writeData(String path,Object object){
        zkClient.writeData(path,object);

    }

    public  void delete(String path){
        zkClient.delete(path);

    }

    public  boolean exists(String path){
        return zkClient.exists(path);

    }

    public  void deleteRecursive(String path){
        zkClient.deleteRecursive(path);

    }

    //对父节点添加监听数据变化。
    public void subscribe(String path){

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.printf("变更的节点为:%s,数据：%s\r\n", dataPath,data );
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.printf("删除的节点为:%s\r\n", dataPath );
            }
        });
    }
    //对父节点添加监听子节点变化。
    public void subscribe2(String path){
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("父节点: " + parentPath+",子节点:"+currentChilds+"\r\n");
            }
        });
    }


    //客户端状态
    public void subscribe3(String path) {
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                if(state== Watcher.Event.KeeperState.SyncConnected){
                    //当我重新启动后start，监听触发
                    System.out.println("连接成功");
                }else if(state== Watcher.Event.KeeperState.Disconnected){
                    System.out.println("连接断开");//当我在服务端将zk服务stop时，监听触发
                }else
                    System.out.println("其他状态"+state);
            }

            @Override
            public void handleNewSession() throws Exception {
                System.out.println("重建session");

            }

            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception {

            }
        });

    }


  /*  @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {



    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {

    }*/
}
