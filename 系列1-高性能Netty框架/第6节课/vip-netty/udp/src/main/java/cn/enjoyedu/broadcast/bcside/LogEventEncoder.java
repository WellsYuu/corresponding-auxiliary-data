package cn.enjoyedu.broadcast.bcside;

import cn.enjoyedu.broadcast.LogMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.List;
/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：编码，将实际的日志实体类编码为DatagramPacket
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogMsg> {
    private final InetSocketAddress remoteAddress;

    //LogEventEncoder 创建了即将被发送到指定的 InetSocketAddress
    // 的 DatagramPacket 消息
    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          LogMsg logMsg, List<Object> out) throws Exception {
        //TODO
    }
}
