package cn.enjoyedu.ch01.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static cn.enjoyedu.ch01.Ch01Const.response;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：
 */
public class BioServerHandler implements Runnable{
    private Socket socket;
    public BioServerHandler(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try(//负责socket读写的输出、输入流
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),
                    true)){
            String message;
            String result;
            //通过输入流读取客户端传输的数据
            //如果已经读到输入流尾部，返回null,退出循环
            //如果得到非空值，就将结果进行业务处理
            while((message = in.readLine())!=null){
                System.out.println("Server accept message:"+message);
                result = response(message);
                //将业务结果通过输出流返回给客户端
                out.println(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }

}
