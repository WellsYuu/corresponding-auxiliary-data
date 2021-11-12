package cn.enjoyedu.ch02.splicing.linebase;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.net.InetSocketAddress;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：
 */
public class LineBaseEchoClient {

    private final String host;

    public LineBaseEchoClient(String host) {
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            final Bootstrap b = new Bootstrap();;/*客户端启动必须*/
            b.group(group)/*将线程组传入*/
                    .channel(NioSocketChannel.class)/*指定使用NIO进行网络传输*/
                    .remoteAddress(new InetSocketAddress(host,LineBaseEchoServer.PORT))/*配置要连接服务器的ip地址和端口*/
                    .handler(new ChannelInitializerImp());
            ChannelFuture f = b.connect().sync();
            System.out.println("已连接到服务器.....");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            //	加分割符：系统回车符
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024*100000));
            ch.pipeline().addLast(new LineBaseClientHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new LineBaseEchoClient("127.0.0.1").start();
    }
}
