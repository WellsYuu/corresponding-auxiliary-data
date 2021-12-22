package com.tl.protocol;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */
public class RedisClient2 {
	private Socket socket;
	private String host;
	private Integer port;

	@Override
	public String toString() {
		return "RedisClient2{" +
				"socket=" + socket +
				", host='" + host + '\'' +
				", port=" + port +
				'}';
	}

	public RedisClient2(String host, Integer port) throws Exception {
		this.host = host;
		this.port = port;
		socket = new Socket(host, port);
	}

/*	public RedisClient2() throws Exception{
		socket = new Socket("192.168.0.12", 6379);
	}*/

	public String get(String key) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("*2").append("\r\n");
		stringBuffer.append("$").append(CommandRedis.get.name().length()).append("\r\n");
		stringBuffer.append(CommandRedis.get).append("\r\n");
		// 汉字 2字节
		stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
		stringBuffer.append(key).append("\r\n");
		socket.getOutputStream().write(stringBuffer.toString().getBytes());
		byte[] b = new byte[2048];
		socket.getInputStream().read(b );
		return new String(b);
	}

	public String set(String key, String value) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("*3").append("\r\n");
		stringBuffer.append("$3").append("\r\n");
		stringBuffer.append(CommandRedis.set).append("\r\n");
		// 汉字 2字节
		stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
		stringBuffer.append(key).append("\r\n");
		stringBuffer.append("$").append(value.getBytes().length).append("\r\n");
		stringBuffer.append(value).append("\r\n");

		socket.getOutputStream().write(stringBuffer.toString().getBytes());
		byte[] b = new byte[2048];
		socket.getInputStream().read(b );
		return new String(b);
	}

	public String setnx(String key, String value) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("*3").append("\r\n");
		stringBuffer.append("$5").append("\r\n");
		stringBuffer.append("setnx").append("\r\n");
		stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
		stringBuffer.append(key).append("\r\n");

		stringBuffer.append("$").append(value.getBytes().length).append("\r\n");
		stringBuffer.append(value).append("\r\n");

		socket.getOutputStream().write(stringBuffer.toString().getBytes());
		byte[] b = new byte[2048];
		socket.getInputStream().read(b );
		return new String(b);
	}

	// pipeline管道
	public void pipeline(String key)throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("*2").append("\r\n");
		//stringBuffer.append("$9").append("\r\n");
		stringBuffer.append("$"+CommandRedis.subscribe.name().length()).append("\r\n");
		stringBuffer.append(CommandRedis.subscribe).append("\r\n");
		//stringBuffer.append("subscribe").append("\r\n");

		// 汉字 2字节
		stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
		stringBuffer.append(key).append("\r\n");

		socket.getOutputStream().write(stringBuffer.toString().getBytes());
		InputStream inputStream = socket.getInputStream();
		while (true) {
			byte[] b = new byte[2048];
			inputStream.read(b );
			System.out.println(new String(b));
		}

	}

	//subscribe 订阅
	public void subscribe(String key)throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("*2").append("\r\n");
		//stringBuffer.append("$9").append("\r\n");
		stringBuffer.append("$"+CommandRedis.subscribe.name().length()).append("\r\n");
		stringBuffer.append(CommandRedis.subscribe).append("\r\n");
		//stringBuffer.append("subscribe").append("\r\n");
		stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
		stringBuffer.append(key).append("\r\n");

		socket.getOutputStream().write(stringBuffer.toString().getBytes());
		InputStream inputStream = socket.getInputStream();
		while (true) {
			byte[] b = new byte[2048];
			inputStream.read(b );
			System.out.println(new String(b));
		}
	}

	public void close(){
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {

/*
		System.out.println(new RedisClient2().set("wukong", "abc"));
		// +OK
		System.out.println("#################");
		System.out.println(new RedisClient2().get("wukong"));
*/

		// 10439
		// 1786
		// 不一定科学，相对
		// 管道模式及性能比对 100000w set
		// 减少网络连接 keep
//		long t = System.currentTimeMillis();
//		RedisClient client = new RedisClient();
//		for (int i = 0; i < 100000; i++) {
//			client.set("count", "c" + i);
//		}
//		System.out.println(System.currentTimeMillis() - t);
//
//		t = System.currentTimeMillis();
//		client = new RedisClient();
//		Pipeline pipeline = client.pipeline();
//		for (int i = 0; i < 100000; i++) {
//			pipeline.set("count", "c" + i);
//		}
//		pipeline.response();
//		System.out.println(System.currentTimeMillis() - t);
//		subClient.close
		// 订阅机制
	/*	RedisClient subClient = new RedisClient();
		Subscribe subscribe = subClient.subscribe("小乔");
		subscribe.sub();*/

	}

}
