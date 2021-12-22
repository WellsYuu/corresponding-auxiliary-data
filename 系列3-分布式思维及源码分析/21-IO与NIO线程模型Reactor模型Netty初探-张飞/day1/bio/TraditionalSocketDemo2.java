package com.tuling.netty.day1.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统bio多线程版本
 */
public class TraditionalSocketDemo2{
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(7777);
		System.out.println("服务端启动...");
		while(true){
			// 获取socket套接字
			// accept()阻塞点
			Socket socket = serverSocket.accept();
			System.out.println("有新客户端连接上来了...");
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// 获取客户端输入流
						InputStream is = socket.getInputStream();
						byte[] b = new byte[1024];
						while(true){
							// 循环读取数据
							// read() 阻塞点
							int data = is.read(b);
							if(data != -1){
								String info = new String(b,0,data,"GBK");
								System.out.println(info);
							}else{
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
