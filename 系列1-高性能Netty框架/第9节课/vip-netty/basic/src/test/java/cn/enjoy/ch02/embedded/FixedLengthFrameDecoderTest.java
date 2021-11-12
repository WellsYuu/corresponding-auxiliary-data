package cn.enjoy.ch02.embedded;

import cn.enjoyedu.ch02.embedded.FixedLengthFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：测试入站
 */
public class FixedLengthFrameDecoderTest {
    @Test
    public void testFramesDecoded() {
        //创建一个 ByteBuf，并存储 9 字节
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        //创建一个EmbeddedChannel，并添加一个FixedLengthFrameDecoder，
        // 其将以 3 字节的帧长度被测试
        EmbeddedChannel channel = new EmbeddedChannel(
            new FixedLengthFrameDecoder(3));

        //返回 false，因为没有一个完整的可供读取的帧
        assertFalse(channel.writeInbound(input.readBytes(1)));
        //还是返回 false，因为没有一个完整的可供读取的帧
        assertFalse(channel.writeInbound(input.readBytes(1)));
        //返回 true，因为已经有了可供读取的帧
        assertTrue(channel.writeInbound(input.readBytes(1)));
        //将剩余的数据写入，准备readInbound测试
        assertTrue(channel.writeInbound(input.readBytes(6)));

        //标记 Channel 为已完成状态
        assertTrue(channel.finish());

        // read messages
        //读取所生成的消息，并且验证是否有 3 帧（切片），其中每帧（切片）都为 3 字节
        ByteBuf read = (ByteBuf) channel.readInbound();
        //和源进行比对
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }

}
