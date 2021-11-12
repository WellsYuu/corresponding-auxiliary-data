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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：Netty客户端的主入口
 */
public class NettyClient implements Runnable{

    private static final Log LOG = LogFactory.getLog(NettyClient.class);

    private ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(1);
    private Channel channel;
    private EventLoopGroup group = new NioEventLoopGroup();

    /*是否用户主动关闭连接的标志值*/
    private volatile boolean userClose = false;
    /*连接是否成功关闭的标志值*/
    private volatile boolean connected = false;

    private AtomicInteger loginRetryCount;

    public boolean isConnected() {
        return connected;
    }

    /*连接服务器*/
    public void connect(int port, String host) throws Exception {

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientInit());
            // 发起异步连接操作
            ChannelFuture future = b.connect(
                    new InetSocketAddress(host, port)).sync();
            channel = future.sync().channel();
            /*连接成功后通知等待线程，连接已经建立*/
            synchronized (this){
                this.connected = true;
                this.notifyAll();
            }

            future.channel().closeFuture().sync();
        } finally {
            if(!userClose){/*非用户主动关闭，说明发生了网络问题，需要进行重连操作*/
                System.out.println("发现异常，可能发生了服务器异常或网络问题，" +
                        "准备进行重连.....");
                //再次发起重连操作
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            try {
                                // 发起重连操作
                                connect(NettyConstant.REMOTE_PORT,
                                        NettyConstant.REMOTE_IP);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else{/*用户主动关闭，释放资源*/
                channel = null;
                group.shutdownGracefully().sync();
                synchronized (this){
                    this.connected = false;
                    this.notifyAll();
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            connect(NettyConstant.REMOTE_PORT, NettyConstant.REMOTE_IP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*------------测试NettyClient--------------------------*/
    public static void main(String[] args) throws Exception {
        NettyClient nettyClient = new NettyClient();
        nettyClient.connect(NettyConstant.REMOTE_PORT, NettyConstant.REMOTE_IP);
    }

    /*------------以下方法供业务方使用--------------------------*/
    public void send(String message) {
        if(channel==null||!channel.isActive()){
            throw new IllegalStateException("和服务器还未未建立起有效连接！，" +
                    "请稍后再试！！");
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
