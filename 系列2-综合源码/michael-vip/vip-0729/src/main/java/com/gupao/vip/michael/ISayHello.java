package com.gupao.vip.michael;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface ISayHello extends Remote{

    public String sayHello(String name) throws RemoteException;
}
