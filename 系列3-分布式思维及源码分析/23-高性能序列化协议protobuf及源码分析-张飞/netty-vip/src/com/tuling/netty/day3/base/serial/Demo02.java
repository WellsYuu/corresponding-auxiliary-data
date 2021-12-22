package com.tuling.netty.day3.base.serial;

import java.nio.ByteBuffer;
import java.util.Arrays;
/**
 * nio 序列化
 * @author 张飞老师
 */
public class Demo02 {

	public static void main(String[] args) {
		int a = 11;
		int b = 22;
		
		// 序列化
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putInt(a);
		buffer.putInt(b);
//		buffer.putInt(2);
		byte[] array = buffer.array();
		System.out.println(Arrays.toString(buffer.array()));
		
		// 反序列化
		ByteBuffer bb = ByteBuffer.wrap(array);
		System.out.println("a: " + bb.getInt());
		System.out.println("b: " + bb.getInt());

	}

}
