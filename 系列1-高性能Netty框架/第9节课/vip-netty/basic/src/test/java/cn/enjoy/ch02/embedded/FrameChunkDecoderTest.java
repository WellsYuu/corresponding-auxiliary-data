package cn.enjoy.ch02.embedded;

import cn.enjoyedu.ch02.embedded.FrameChunkDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：只允许最大的帧的字节大小为设定的值,测试异常处理
 */
public class FrameChunkDecoderTest {
    @Test
    public void testFramesDecoded() {
        //创建一个 ByteBuf，并向它写入 9 字节
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        //创建一个 EmbeddedChannel，并向其安装允许一个帧最大为3字节的
        // FrameChunkDecoder
        EmbeddedChannel channel = new EmbeddedChannel(
            new FrameChunkDecoder(3));

        //向它写入 2 字节，并断言它们将会产生一个新帧
        assertTrue(channel.writeInbound(input.readBytes(2)));
        try {
            //写入一个 4 字节大小的帧，并捕获预期的TooLongFrameException
            channel.writeInbound(input.readBytes(4));
        } catch (TooLongFrameException e) {
            e.printStackTrace();
        }
        //写入剩余的2字节，并断言将会产生一个有效帧
        assertTrue(channel.writeInbound(input.readBytes(3)));
        //将该 Channel 标记为已完成状态
        assertTrue(channel.finish());

    }
}
