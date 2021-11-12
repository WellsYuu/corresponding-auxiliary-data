package cn.enjoyedu.server;


import cn.enjoyedu.vo.MessageType;
import cn.enjoyedu.vo.MyHeader;
import cn.enjoyedu.vo.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：心跳应答
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

	private static final Log LOG = LogFactory.getLog(HeartBeatRespHandler.class);

    public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
		MyMessage message = (MyMessage) msg;
		// 返回心跳应答消息
		if (message.getMyHeader() != null
			&& message.getMyHeader().getType() == MessageType.HEARTBEAT_REQ
				.value()) {
//			LOG.info("Receive client heart beat message : ---> "
//				+ message);
			MyMessage heartBeat = buildHeatBeat();
//			LOG.info("Send heart beat response message to client : ---> "
//					+ heartBeat);
			ctx.writeAndFlush(heartBeat);
			ReferenceCountUtil.release(msg);
		} else
			ctx.fireChannelRead(msg);
    }

    /*心跳应答报文*/
    private MyMessage buildHeatBeat() {
		MyMessage message = new MyMessage();
		MyHeader myHeader = new MyHeader();
		myHeader.setType(MessageType.HEARTBEAT_RESP.value());
		message.setMyHeader(myHeader);
		return message;
    }

}
