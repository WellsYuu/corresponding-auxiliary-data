package cn.enjoyedu.ch02.serializable.msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.net.InetSocketAddress;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：
 */
public class ClientMsgPackEcho {

    private final String host;

    public ClientMsgPackEcho(String host) {
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            final Bootstrap b = new Bootstrap();;/*客户端启动必须*/
            b.group(group)/*将线程组传入*/
                    .channel(NioSocketChannel.class)/*指定使用NIO进行网络传输*/
                    /*配置要连接服务器的ip地址和端口*/
                    .remoteAddress(
                            new InetSocketAddress(host,ServerMsgPackEcho.PORT))
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

            //这里设置报文的包头长度来避免粘包
            ch.pipeline().addLast("frameEncoder",
                    new LengthFieldPrepender(2));
            //对发送的数据进行序列化
            ch.pipeline().addLast(new MsgPackEncoder());
            //处理服务器的应答
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            ch.pipeline().addLast(new MsgPackClientHandler(5));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ClientMsgPackEcho("127.0.0.1").start();
    }
}
