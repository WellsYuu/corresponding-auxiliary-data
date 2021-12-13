package com.gupao.vip.michael.MulticastDemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MulticastClient {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress group=InetAddress.getByName("224.5.6.7");

        try {
            MulticastSocket socket=new MulticastSocket(8888);

            socket.joinGroup(group);  //加到指定的组里面

            byte[] buf=new byte[256];
            while(true){
                DatagramPacket msgPacket=new DatagramPacket(buf,buf.length);
                socket.receive(msgPacket);

                String msg=new String(msgPacket.getData());
                System.out.println("接收到的数据："+msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
