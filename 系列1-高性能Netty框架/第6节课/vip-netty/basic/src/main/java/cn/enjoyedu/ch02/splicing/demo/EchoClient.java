package cn.enjoyedu.ch02.splicing.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：netty的客户端
 */
public class EchoClient {

    private final int port;
    private final String host;

    public EchoClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        //EventLoopGroup work = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();/*客户端启动必备*/
            b.group(group)
                .channel(NioSocketChannel.class)/*指明使用NIO进行网络通讯*/
                .remoteAddress(new InetSocketAddress(host,port))/*配置远程服务器的地址*/
                .handler(new EchoClientHandler());
            ChannelFuture f = b.connect().sync();/*连接到远程节点，阻塞等待直到连接完成*/
            /*阻塞，直到channel关闭*/
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient(9999,"127.0.0.1").start();
    }
}
