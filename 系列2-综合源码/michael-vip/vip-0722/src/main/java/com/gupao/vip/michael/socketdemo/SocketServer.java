package com.gupao.vip.michael.socketdemo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SocketServer {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=null;

        try{
            serverSocket=new ServerSocket(8888);  //启动一个服务
            while(true){
                Socket socket=serverSocket.accept();  //等待一个接收请求
                new Thread(()->{
                    try {
                        //读取数据
                        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        //发送数据
                        PrintWriter writer =new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                        while(true){
                            String clientData=reader.readLine(); //读取客户端发送过来的消息

                            if(clientData==null){
                                break;
                            }

                            System.out.println("服务端接收到的数据："+clientData);

                            writer.println("Hello Mic; ^^");
                            writer.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        }catch (Exception e){

        }finally {
            if(serverSocket!=null){
                serverSocket.close();
            }
        }
    }
}
