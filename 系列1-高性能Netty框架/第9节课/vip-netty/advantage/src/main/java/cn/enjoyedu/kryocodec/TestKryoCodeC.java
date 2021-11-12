package cn.enjoyedu.kryocodec;

import cn.enjoyedu.vo.MyHeader;
import cn.enjoyedu.vo.MyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：序列化器的测试类
 */
public class TestKryoCodeC {

    public MyMessage getMessage() {
		MyMessage myMessage = new MyMessage();
		MyHeader myHeader = new MyHeader();
		myHeader.setLength(123);
		myHeader.setSessionID(99999);
		myHeader.setType((byte) 1);
		myHeader.setPriority((byte) 7);
		Map<String, Object> attachment = new HashMap<String, Object>();
		for (int i = 0; i < 10; i++) {
			attachment.put("ciyt --> " + i, "lilinfeng " + i);
		}
		myHeader.setAttachment(attachment);
		myMessage.setMyHeader(myHeader);
		myMessage.setBody("abcdefg-----------------------AAAAAA");
		return myMessage;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
		TestKryoCodeC testC = new TestKryoCodeC();

		for (int i = 0; i < 5; i++) {
			ByteBuf sendBuf = Unpooled.buffer();
            MyMessage message = testC.getMessage();
            System.out.println("Encode:"+message + "[body ] " + message.getBody());
            KryoSerializer.serialize(message, sendBuf);
			MyMessage decodeMsg = (MyMessage)KryoSerializer.deserialize(sendBuf);
			System.out.println("Decode:"+decodeMsg + "<body > "
					+ decodeMsg.getBody());
			System.out
				.println("-------------------------------------------------");
		}

    }

}
