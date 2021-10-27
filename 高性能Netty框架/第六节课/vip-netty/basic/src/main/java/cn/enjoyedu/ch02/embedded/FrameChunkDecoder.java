package cn.enjoyedu.ch02.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：只允许最大的帧的字节大小为设定的值，测试异常处理
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {
    private final int maxFrameSize;

    //指定将要产生的帧的最大允许大小
    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                          List<Object> out)
        throws Exception {
        int readableBytes = in.readableBytes();
        if (readableBytes > maxFrameSize) {
            // discard the bytes
            //如果该帧太大，则丢弃它并抛出一个 TooLongFrameException……
            in.clear();
            throw new TooLongFrameException();
        }
        //……否则，从 ByteBuf 中读取一个新的帧
        ByteBuf buf = in.readBytes(readableBytes);
        //将该帧添加到解码 读取一个新的帧消息的 List 中
        out.add(buf);
    }
}
