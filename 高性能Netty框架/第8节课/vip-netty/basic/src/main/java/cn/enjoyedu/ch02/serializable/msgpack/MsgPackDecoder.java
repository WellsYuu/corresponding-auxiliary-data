package cn.enjoyedu.ch02.serializable.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
                          List<Object> out) throws Exception {
        int length = msg.readableBytes();
        byte[] array = new byte[length];
        msg.getBytes(msg.readerIndex(),array,0,length);
        MessagePack messagePack = new MessagePack();
        out.add(messagePack.read(array,User.class));

    }
}
