package cn.enjoy.ch02.embedded;

import cn.enjoyedu.ch02.embedded.AbsIntegerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：测试出站处理
 */
public class AbsIntegerEncoderTest {
    @Test
    public void testEncoded() {
        //(1) 创建一个 ByteBuf，并且写入 9 个负整数
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        //(2) 创建一个EmbeddedChannel，并安装一个要测试的 AbsIntegerEncoder
        EmbeddedChannel channel = new EmbeddedChannel(
                new AbsIntegerEncoder());

    }
}
