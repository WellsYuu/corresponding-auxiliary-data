package com.gupao.vip.michael.MulticastDemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MulticastServer {

    public static void main(String[] args) {
        try {
            //地址段：224.0.0.0 - 239.255.255.255
            InetAddress group=InetAddress.getByName("224.5.6.7");

            MulticastSocket socket=new MulticastSocket();

            for(int i=0;i<10;i++){
                String data="Hello Mic";
                byte[] bytes=data.getBytes();
                socket.send(new DatagramPacket(bytes,bytes.length,group,8888));
                TimeUnit.SECONDS.sleep(2);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
