package cn.enjoyedu.broadcast.acceptside;

import cn.enjoyedu.broadcast.LogMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：解码，将DatagramPacket解码为实际的日志实体类
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          DatagramPacket datagramPacket, List<Object> out)
        throws Exception {
        //获取对 DatagramPacket 中的数据（ByteBuf）的引用
        ByteBuf data = datagramPacket.content();
        //获得发送时间
        long sendTime = data.readLong();
        System.out.println("接受到"+sendTime+"发送的消息");
        //获得消息的id
        long msgId = data.readLong();
        //获得分隔符SEPARATOR
        byte sepa = data.readByte();
        //获取读索引的当前位置，就是分隔符的索引+1
        int idx = data.readerIndex();
        //提取日志消息，从读索引开始，到最后为日志的信息
        String sendMsg = data.slice(idx ,
            data.readableBytes()).toString(CharsetUtil.UTF_8);
        //构建一个新的 LogMsg 对象，并且将它添加到（已经解码的消息的）列表中
        LogMsg event = new LogMsg(datagramPacket.sender(),
                msgId, sendMsg);
        //作为本handler的处理结果，交给后面的handler进行处理
        out.add(event);
    }
}
