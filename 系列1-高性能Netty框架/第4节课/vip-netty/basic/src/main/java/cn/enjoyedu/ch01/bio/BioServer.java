package cn.enjoyedu.ch01.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.enjoyedu.ch01.Ch01Const.DEFAULT_PORT;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：bio的服务端主程序
 */
public class BioServer {
    //服务器端必须
    private static ServerSocket server;
    //线程池，处理每个客户端的请求
    private static ExecutorService executorService
            = Executors.newFixedThreadPool(5);

    private static void start() throws IOException{

        try{
            //通过构造函数创建ServerSocket
            //如果端口合法且空闲，服务端就监听成功
            server = new ServerSocket(DEFAULT_PORT);
            System.out.println("服务器已启动，端口号：" + DEFAULT_PORT);
            while(true){
                Socket socket= server.accept();
                System.out.println("有新的客户端连接----" );
                //当有新的客户端接入时，打包成一个任务，投入线程池
                executorService.execute(new BioServerHandler(socket));
            }
        }finally{
            if(server!=null){
                server.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        start();
    }

}
