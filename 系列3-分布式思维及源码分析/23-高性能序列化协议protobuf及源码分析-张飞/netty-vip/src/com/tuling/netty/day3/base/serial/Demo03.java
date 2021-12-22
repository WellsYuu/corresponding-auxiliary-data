package com.tuling.netty.day3.base.serial;

import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * netty序列化 
 * @author 张飞老师
 */
public class Demo03 {

	public static void main(String[] args) {

		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(11);
		buffer.writeInt(22);
		buffer.writeLong(23);
		buffer.writeLong(23);
		
		// byte数组的大小由buffer中写指针的位置决定
		// 往ChannelBuffer中写数据的时候，这个写指针就会移动写的数据的长度
		byte[] bytes = new byte[buffer.writerIndex()];
		buffer.readBytes(bytes); // 序列化
		System.out.println(Arrays.toString(bytes));
		
		// 反序列化
		ChannelBuffer wrappedBuffer = ChannelBuffers.wrappedBuffer(bytes);
		System.out.println(wrappedBuffer.readInt());
		System.out.println(wrappedBuffer.readInt());
	}
}
