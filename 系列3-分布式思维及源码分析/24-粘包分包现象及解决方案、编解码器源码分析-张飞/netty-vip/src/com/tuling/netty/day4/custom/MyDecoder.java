/**
 * 
 */
package com.tuling.netty.day4.custom;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author 张飞老师
 */
public class MyDecoder extends FrameDecoder{
	// 包头
	private static final int HEAD_FLAG = -32323231;
	// 数据包基本长度
	private final static int BASE_LENGTH = 4 + 4;
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		
		// 收到数据之后，先判断buffer中可读的数据长度是否大于数据包的基本长度
		if(buffer.readableBytes() > BASE_LENGTH){
			// 防止socket攻击：
			if(buffer.readableBytes() > 4096 * 2){ // 4k
				System.out.println("socket 攻击了");
				buffer.skipBytes(buffer.readableBytes());
			}
			// 记录包头开始的位置
			int headIndex;
			while(true){
				headIndex = buffer.readerIndex();
				buffer.markReaderIndex();
				// 代码很关键
				if(buffer.readableBytes() < 4){// 包头的长度
					buffer.readerIndex(headIndex);
					return null;
				}
				
				// 此时说明包头的长度是足够的
				// 正好读取的是包头
				if(buffer.readInt() == HEAD_FLAG ){
					break;
				}
			
				// [1,2,3,4] 1 1 1 1 1
				// 如果不是包头,需要略过一个字节，在略过之前，需要还原读指针位置
				buffer.resetReaderIndex();
				buffer.readByte();// 略过一个字节
				
				if(buffer.readableBytes() < BASE_LENGTH){
					return null;
				}
				
			}
			
			// 此时说明有数据包到来
			// 做标记(记住当前读指针的位置)
			// buffer.markReaderIndex();
			
			// 1.读长度
			int dataLength = buffer.readInt();
			
			if(buffer.readableBytes() < dataLength){
				// 说明数据本身的长度还不够， 肯定要继续等待后面的数据到来
				// 还原读指针的位置
				buffer.readerIndex(headIndex);
				return null;
			}
			
			// 此时说明数据包已经位置
			// 2.读数据本身
			byte[] dst = new byte[dataLength];
			buffer.readBytes(dst);
			// 继续传递下去
			// ?
			// 如果此时buffer中的数据还没有读完，那么剩下的数据怎么办？
			return new String(dst);
			
		}
		// return null 表示此时的数据包不完整，需要继续等待下一个数据包的到来 ？
		return null;
	}

}
