package cn.enjoyedu.ch01.aio.client;

import java.util.Scanner;

import static cn.enjoyedu.ch01.Ch01Const.DEFAULT_PORT;
import static cn.enjoyedu.ch01.Ch01Const.DEFAULT_SERVER_IP;


/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：aio的客户端主程序
 */
public class AioClient {

    //IO通信处理器
    private static AioClientHandler clientHandle;

    public static void start(){
        if(clientHandle!=null)
            return;
        clientHandle = new AioClientHandler(DEFAULT_SERVER_IP,DEFAULT_PORT);
        //负责网络通讯的线程
        new Thread(clientHandle,"Client").start();
    }
    //向服务器发送消息
    public static boolean sendMsg(String msg) throws Exception{
        if(msg.equals("q")) return false;
        clientHandle.sendMessag(msg);
        return true;
    }

    public static void main(String[] args) throws Exception{
        AioClient.start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while(AioClient.sendMsg(scanner.nextLine()));
    }

}
