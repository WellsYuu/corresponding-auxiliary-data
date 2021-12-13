package com.gupao.vip.michael.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 * 选主的服务
 */
public class MasterSelector {

    private ZkClient zkClient;

    private final static String MASTER_PATH="/master"; //需要争抢的节点

    private IZkDataListener dataListener; //注册节点内容变化

    private UserCenter server;  //其他服务器

    private UserCenter master;  //master节点

    private boolean isRunning=false;

    ScheduledExecutorService scheduledExecutorService= Executors.newScheduledThreadPool(1);

    public MasterSelector(UserCenter server,ZkClient zkClient) {
        System.out.println("["+server+"] 去争抢master权限");
        this.server = server;
        this.zkClient=zkClient;

        this.dataListener= new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //节点如果被删除, 发起选主操作
                chooseMaster();
            }
        };
    }

    public void start(){
        //开始选举
        if(!isRunning){
            isRunning=true;
            zkClient.subscribeDataChanges(MASTER_PATH,dataListener); //注册节点事件
            chooseMaster();
        }
    }


    public void stop(){
        //停止
        if(isRunning){
            isRunning=false;
            scheduledExecutorService.shutdown();
            zkClient.unsubscribeDataChanges(MASTER_PATH,dataListener);
            releaseMaster();
        }
    }


    //具体选master的实现逻辑
    private void chooseMaster(){
        if(!isRunning){
            System.out.println("当前服务没有启动");
            return ;
        }
        try {
            zkClient.createEphemeral(MASTER_PATH, server);
            master=server; //把server节点赋值给master
            System.out.println(master+"->我现在已经是master，你们要听我的");

            //定时器
            //master释放(master 出现故障）,没5秒钟释放一次
            scheduledExecutorService.schedule(()->{
                releaseMaster();//释放锁
            },2, TimeUnit.SECONDS);
        }catch (ZkNodeExistsException e){
            //表示master已经存在
            UserCenter userCenter=zkClient.readData(MASTER_PATH,true);
            if(userCenter==null) {
                System.out.println("启动操作：");
                chooseMaster(); //再次获取master
            }else{
                master=userCenter;
            }
        }
    }

    private void releaseMaster(){
        //释放锁(故障模拟过程)
        //判断当前是不是master，只有master才需要释放
        if(checkIsMaster()){
            zkClient.delete(MASTER_PATH); //删除
        }
    }


    private boolean checkIsMaster(){
        //判断当前的server是不是master
        UserCenter userCenter=zkClient.readData(MASTER_PATH);
        if(userCenter.getMc_name().equals(server.getMc_name())){
            master=userCenter;
            return true;
        }
        return false;
    }

}
