package cn.enjoy.ch02.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

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

    }


}
