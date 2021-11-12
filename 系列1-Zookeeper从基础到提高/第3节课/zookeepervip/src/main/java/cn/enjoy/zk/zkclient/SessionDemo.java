package cn.enjoy.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;


public class SessionDemo {

    private final static String CONNECTSTRING="192.168.30.10:2181";

    public static void main(String[] args) {
        ZkClient zkClient=new ZkClient(CONNECTSTRING,4000);

        System.out.println(zkClient+" - > success");
    }
}
