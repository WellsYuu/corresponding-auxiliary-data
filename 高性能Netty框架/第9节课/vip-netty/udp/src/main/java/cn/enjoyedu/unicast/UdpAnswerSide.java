package cn.enjoyedu.unicast;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：应答端
 */
public class UdpAnswerSide {

    public final static String ANSWER = "古诗来了：";

    public void run(int port) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            /*和tcp的不同，udp没有接受连接的说法，所以即使是接收端，也使用Bootstrap*/
            Bootstrap b = new Bootstrap();
            /*由于我们用的是UDP协议，所以要用NioDatagramChannel来创建*/
            b.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new AnswerHandler());
            //没有接受客户端连接的过程，监听本地端口即可
            ChannelFuture f = b.bind(port).sync();
            System.out.println("应答服务已启动.....");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
    public static void main(String [] args) throws Exception{
        int port = 8080;
        new UdpAnswerSide().run(port);
    }
}
