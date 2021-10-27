package cn.enjoyedu.ch01.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：IO通信处理器，负责连接服务器，对外暴露对服务端发送数据的API
 */
public class AioClientHandler
        implements CompletionHandler<Void,AioClientHandler>,Runnable {

    private AsynchronousSocketChannel clientChannel;
    private String host;
    private int port;

    private CountDownLatch latch;//防止线程退出

    public AioClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            //创建一个实际异步的客户端通道
            clientChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        //创建CountDownLatch，因为是异步调用，下面的connect不会阻塞，
        // 那么整个run方法会迅速结束，那么负责网络通讯的线程也会迅速结束
        latch = new CountDownLatch(1);
        //发起异步连接操作，回调参数就是这个实例本身，
        // 如果连接成功会回调这个实例的completed方法
        clientChannel.connect(new InetSocketAddress(host,port),
                        null,this);
        try {

            latch.await();
            clientChannel.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //连接成功，这个方法会被系统调用
    @Override
    public void completed(Void result, AioClientHandler attachment) {
        System.out.println("已经连接到服务端。");
    }

    //连接失败，这个方法会被系统调用
    @Override
    public void failed(Throwable exc, AioClientHandler attachment) {
        System.err.println("连接失败。");
        exc.printStackTrace();
        latch.countDown();
        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //对外暴露对服务端发送数据的API
    public void sendMessag(String msg){
        /*为了把msg变成可以在网络传输的格式*/
        byte[] bytes = msg.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();

        /*进行异步写，同样的这个方法会迅速返回，
         需要提供一个接口让系统在一次网络写操作完成后通知我们的应用程序。
        所以我们传入一个实现了CompletionHandler的AioClientWriteHandler
        第1个writeBuffer，表示我们要发送给服务器的数据；
        第2个writeBuffer，考虑到网络写有可能无法一次性将数据写完，需要进行多次网络写，
        所以将writeBuffer作为附件传递给AioClientWriteHandler。
        */
        clientChannel.write(writeBuffer,writeBuffer,
                new AioClientWriteHandler(clientChannel,latch));


    }

}
