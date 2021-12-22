package com.tuling.netty.day3.java.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * java原始序列化：
 * ObjectOutputStream	序列化
 * ObjectInputStream  反序列化
 * 
 * @author 张飞老师
 */
public class JavaSeri {

	public static void main(String[] args) throws Exception{
		// 序列化
		byte[] result = serialize();
		System.out.println(Arrays.toString(result));
		
		// 反序列化
		SubscribeReq req = deserialize(result);
		System.out.println(req.getSubReqID());
		System.out.println(req.getUserName());
		System.out.println(req.getProductName());
		System.out.println(req.getAddressList().get(0));
		System.out.println(req.getAddressList().get(1));
	}
	
	// 序列化
	public static byte[] serialize() throws Exception{
		SubscribeReq req = new SubscribeReq();
		
		req.setSubReqID(1);
		req.setUserName("abc");
		req.setProductName("netty");
		
		List<String> addressList = new ArrayList<String>();
		addressList.add("长沙");
		addressList.add("深圳");
		req.setAddressList(addressList);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
		// 把SubscribeReq对象写入ByteArrayOutputStream中
		objectOutputStream.writeObject(req);
		// 从ByteArrayOutputStream 获取序列化好的字节数组
		byte[] byteArray = baos.toByteArray();
		return byteArray ;
	}
	
	// 反序列化
	public static SubscribeReq deserialize(byte[] byteArray) throws Exception{
		ObjectInputStream objectOutputStream = new ObjectInputStream(
				new ByteArrayInputStream(byteArray));
		SubscribeReq req = (SubscribeReq)objectOutputStream.readObject();
		return req;
	}
}
