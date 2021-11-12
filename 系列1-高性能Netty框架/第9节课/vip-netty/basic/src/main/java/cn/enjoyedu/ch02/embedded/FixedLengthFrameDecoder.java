package cn.enjoyedu.ch02.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：产生固定大小的帧，测试入站
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    //指定要生成的帧的长度
    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                          List<Object> out) throws Exception {
        //检查是否有足够的字节可以被读取，以生成下一个帧
        while (in.readableBytes() >= frameLength) {
            //从 ByteBuf 中读取一个新帧
            ByteBuf buf = in.readBytes(frameLength);
            //将该帧添加到已被解码的消息列表中
            out.add(buf);
        }
    }
}
