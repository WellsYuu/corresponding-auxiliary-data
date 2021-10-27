package cn.enjoyedu.ch02.splicing.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import java.net.InetSocketAddress;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/25
 * 类说明：
 */
public class DelimiterEchoServer {

    //自定义的分割符
    public static final String DELIMITER_SYMBOL = "MarkJames";
    public static final int PORT = 9997;

    public static void main(String[] args) throws InterruptedException {
        DelimiterEchoServer delimiterEchoServer = new DelimiterEchoServer();
        System.out.println("服务器即将启动");
        delimiterEchoServer.start();
    }

    public void start() throws InterruptedException {
        final DelimiterServerHandler serverHandler = new DelimiterServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            ServerBootstrap b = new ServerBootstrap();/*服务端启动必须*/
            b.group(group)/*将线程组传入*/
                .channel(NioServerSocketChannel.class)/*指定使用NIO进行网络传输*/
                .localAddress(new InetSocketAddress(PORT))/*指定服务器监听端口*/
                /*服务端每接收到一个连接请求，就会新启一个socket通信，也就是channel，
                所以下面这段代码的作用就是为这个子channel增加handle*/
                .childHandler(new ChannelInitializerImp());
            ChannelFuture f = b.bind().sync();/*异步绑定到服务器，sync()会阻塞直到完成*/
            System.out.println("服务器启动完成，等待客户端的连接和数据.....");
            f.channel().closeFuture().sync();/*阻塞直到服务器的channel关闭*/
        } finally {
            group.shutdownGracefully().sync();/*优雅关闭线程组*/
        }
    }

    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            //1）	加分割符  自定义的
            ByteBuf delimiter = Unpooled.copiedBuffer(DELIMITER_SYMBOL.getBytes());
            ch.pipeline().addLast(
                    new DelimiterBasedFrameDecoder(1024,delimiter));
            ch.pipeline().addLast(new DelimiterServerHandler());
        }
    }

}
