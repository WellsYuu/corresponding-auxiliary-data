package cn.enjoyedu.client;

import cn.enjoyedu.kryocodec.KryoDecoder;
import cn.enjoyedu.kryocodec.KryoEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：客户端Handler的初始化
 */
public class ClientInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /*剥离接收到的消息的长度字段，拿到实际的消息报文的字节数组*/
        ch.pipeline().addLast("frameDecoder",
                new LengthFieldBasedFrameDecoder(65535,
                        0,2,0,
                        2));

        /*给发送出去的消息增加长度字段*/
        ch.pipeline().addLast("frameEncoder",
                new LengthFieldPrepender(2));

        /*反序列化，将字节数组转换为消息实体*/
        ch.pipeline().addLast(new KryoDecoder());
        /*序列化，将消息实体转换为字节数组准备进行网络传输*/
        ch.pipeline().addLast("MessageEncoder",
                new KryoEncoder());

        /*超时检测*/
        ch.pipeline().addLast("readTimeoutHandler",
                new ReadTimeoutHandler(10));

        /*发出登录请求*/
        ch.pipeline().addLast("LoginAuthHandler",
                new LoginAuthReqHandler());

        /*发出心跳请求*/
        ch.pipeline().addLast("HeartBeatHandler",
                new HeartBeatReqHandler());
    }
}
