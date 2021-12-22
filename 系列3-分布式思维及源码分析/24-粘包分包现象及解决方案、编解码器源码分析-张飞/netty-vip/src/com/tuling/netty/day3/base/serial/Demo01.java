package com.tuling.netty.day3.base.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * 原始int转byte数组
 * @author 张飞老师
 */
public class Demo01 {

	public static void main(String[] args) throws Exception {
		int a = 11;
		int b = 22;
		int c = 88;
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		//os.write(a);// 为什么不用这个？
		os.write(intToBytes(a));
		os.write(intToBytes(b));
		os.write(intToBytes(c));
		byte[] byteArray = os.toByteArray();
		System.out.println(Arrays.toString(byteArray));
		
		ByteArrayInputStream is = new ByteArrayInputStream(byteArray);
		byte[] aBytes = new byte[4];
		byte[] bBytes = new byte[4];
		byte[] cBytes = new byte[4];
		is.read(aBytes);
		is.read(bBytes);
		is.read(cBytes);
		System.out.println("a: " + bytesToInt(aBytes));
		System.out.println("b: " + bytesToInt(bBytes));
		System.out.println("c: " + bytesToInt(cBytes));
	}
	
	/**
	 * byte数组转int; 低位在前，高位在后
	 */    
	public static int bytesToInt(byte[] byteArray) {  
	    return (byteArray[0]&0xFF)|
	    		((byteArray[1]<<1*8) & 0xFF00)|
	    		((byteArray[2]<<2*8) & 0xFF0000)|
	    		((byteArray[3]<<3*8) & 0xFF000000);  
	}  
	/**
	 * 将int数值转换为占四个字节的byte数组， 低位在前，高位在后 
	 */    
	public static byte[] intToBytes(int value)   
	{
	    byte[] byteArray = new byte[4];  
	    // 最高位放在最后一个字节 ，也就是向右移动3个字节 = 24位
	    byteArray[3] = (byte) ((value & 0xFF000000)>>3*8);// 最高位，放在字节数组最后
	    byteArray[2] = (byte) ((value & 0x00FF0000)>>2*8);// 左边第二个字节
	    byteArray[1] = (byte) ((value & 0x0000FF00)>>1*8);    
	    byteArray[0] = (byte) ((value & 0x000000FF));     // 最低位     
	    //[11,0,0,0]
	    return byteArray;  
	}
}
