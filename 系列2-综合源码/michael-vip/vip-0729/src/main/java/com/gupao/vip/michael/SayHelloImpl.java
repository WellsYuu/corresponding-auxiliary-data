package com.gupao.vip.michael;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SayHelloImpl extends UnicastRemoteObject implements ISayHello{

    public SayHelloImpl() throws RemoteException {
    }

    public String sayHello(String name) throws RemoteException {
        return "Hello Mic->"+name;
    }
}
