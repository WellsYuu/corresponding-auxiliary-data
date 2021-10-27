package cn.enjoyedu.ch01.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import static cn.enjoyedu.ch01.Ch01Const.DEFAULT_PORT;
import static cn.enjoyedu.ch01.Ch01Const.DEFAULT_SERVER_IP;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：客户端
 */
public class BioClient {

    public static void main(String[] args) throws InterruptedException,
            IOException {
        //通过构造函数创建Socket，并且连接指定地址和端口的服务端
        Socket socket =  new Socket(DEFAULT_SERVER_IP,DEFAULT_PORT);
        System.out.println("请输入请求消息：");
        //启动读取服务端输出数据的线程
        new ReadMsg(socket).start();
        PrintWriter pw = null;
        //允许客户端在控制台输入数据，然后送往服务器
        while(true){
            pw = new PrintWriter(socket.getOutputStream());
            pw.println(new Scanner(System.in).next());
            pw.flush();
        }
    }

    //读取服务端输出数据的线程
    private static class ReadMsg extends Thread {
        Socket socket;

        public ReadMsg(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //负责socket读写的输入流
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))){
                String line = null;
                //通过输入流读取服务端传输的数据
                //如果已经读到输入流尾部，返回null,退出循环
                //如果得到非空值，就将结果进行业务处理
                while((line=br.readLine())!=null){
                    System.out.printf("%s\n",line);
                }
            } catch (SocketException e) {
                System.out.printf("%s\n", "服务器断开了你的连接");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                clear();
            }
        }

        //必要的资源清理工作
        private void clear() {
            if (socket != null)
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
