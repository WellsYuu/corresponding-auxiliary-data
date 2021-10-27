package cn.enjoy.ch02.embedded;

import cn.enjoyedu.ch02.embedded.FrameChunkDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：测试异常处理
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
        //创建一个 EmbeddedChannel，并向其安装一个帧大小为 3 字节的
        // FixedLengthFrameDecoder
        EmbeddedChannel channel = new EmbeddedChannel(
                new FrameChunkDecoder(3));

    }
}
