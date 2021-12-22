/**
 * 
 */
package com.tuling.netty.day4.socket;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 防止socket字节流攻击
 * @author 张飞老师
 */
public class MyDecoder extends FrameDecoder {
	// 包头
	public static final int HEAD_FLAG = -32523523;
	public static final int BASE_LENGTH = 4 + 4;
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		//1.首先收到数据之后，判断buffer中可读的长度必须要大于数据包基本长度
		if(buffer.readableBytes() > BASE_LENGTH){
			//防止socket字节流攻击
			if(buffer.readableBytes() > 4096){// 2048 可以测试下，会发生socket攻击
				System.out.println("socket");
				buffer.skipBytes(buffer.readableBytes());
			}
			//记录包头开始的index
			int beginReader;
			while(true){
				beginReader = buffer.readerIndex();
				buffer.markReaderIndex();
				// 关键代码：读取包头，首先判断是否有包头的长度，如果没有需要继续等待数据
				if(buffer.readableBytes() < 4){
					buffer.readerIndex(beginReader);
					return null;
				}
				// 一次读取4字节int，看是否是包头，如果是包头，跳出循环，然后继续读取后面的数据[长度+数据]
				if(buffer.readInt() == HEAD_FLAG){
					break;
				}
				//未读到包头，略过一个字节
				buffer.resetReaderIndex();
				buffer.readByte();
				
				//长度又变得不满足
				if(buffer.readableBytes() < BASE_LENGTH){
					return null;
				}
			}
			// 说明此时有数据包到来，并且是从长度开始读取
			// 上面已经做了标记了，此处不需要在做标记
			// buffer.markReaderIndex();
			// 1.读长度
			int dataLength = buffer.readInt();
			// 2.读数据本身
			if(buffer.readableBytes() < dataLength){
				// 说明数据包本身不完整，继续等待后面的数据包到来
				//还原读指针
				buffer.readerIndex(beginReader);
				return null;
			}
			// 说明buffer中的数据长度足够
			byte[] bytes = new byte[dataLength];
			buffer.readBytes(bytes);
			return new String(bytes);
		}
		// return null 表示此时的数据包不完整，继续等待下一个数据包的到来
		return null;
	}

}
