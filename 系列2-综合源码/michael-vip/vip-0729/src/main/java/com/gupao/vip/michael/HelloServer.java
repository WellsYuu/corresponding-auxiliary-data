package com.gupao.vip.michael;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class HelloServer {

    public static void main(String[] args) {
        try {
            ISayHello hello=new SayHelloImpl();

            LocateRegistry.createRegistry(8888);

            Naming.bind("rmi://localhost:8888/sayHello",hello);

            System.out.println("server start success");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
