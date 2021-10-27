package cn.enjoyedu;

import cn.enjoyedu.client.ClientInit;
import cn.enjoyedu.vo.MessageType;
import cn.enjoyedu.vo.MyHeader;
import cn.enjoyedu.vo.MyMessage;
import cn.enjoyedu.vo.NettyConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：客户端的主入口
 */
public class NettyClient {

    private static final Log LOG = LogFactory.getLog(NettyClient.class);

    private ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(1);

    private Channel channel;
    private volatile boolean userClose = false;

    private EventLoopGroup group = new NioEventLoopGroup();

    public void connect(int port, String host) throws Exception {

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientInit());
            // 发起异步连接操作
            ChannelFuture future = b.connect(
                    new InetSocketAddress(host, port),
                    new InetSocketAddress(NettyConstant.LOCALIP,
                            NettyConstant.LOCAL_PORT)).sync();
            channel = future.channel();
            send("这是一条业务消息");

            future.channel().closeFuture().sync();
        } finally {
            if(!userClose){
                //再次发起重连操作
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            try {
                                // 发起重连操作
                                connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        NettyClient nettyClient = new NettyClient();
        nettyClient.connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
    }


    /*------------以下方法供业务方使用--------------------------*/
    public void send(String message) {
        if(channel==null||!channel.isActive()){
            throw new IllegalStateException("和服务器未建立起有效连接！");
        }
        MyMessage msg = new MyMessage();
        MyHeader myHeader = new MyHeader();
        myHeader.setType(MessageType.SERVICE_REQ.value());
        msg.setMyHeader(myHeader);
        msg.setBody(message);
        channel.writeAndFlush(msg);
    }

    public void close() {
        userClose = true;
        channel.close();
    }

}
