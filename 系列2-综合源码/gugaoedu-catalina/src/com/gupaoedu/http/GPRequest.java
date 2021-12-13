package com.gupaoedu.http;

import java.io.InputStream;
import java.util.Map;

public class GPRequest {

	private String method;
	
	private String url;
	
	public GPRequest(InputStream is){
		//要从HTTP协议中获取到客户端带过来的信息
		//说白了就是一个字符串
		try{
			String content = "";
			
			byte [] buff = new byte[1024];
			int len = 0;
			if((len = is.read(buff)) > 0){
				content = new String(buff,0,len);
			}
			System.out.println(content);
			String line = content.split("\\n")[0];
			String [] arr = line.split("\\s");
			
			this.method = arr[0];
			this.url = arr[1].split("\\?")[0];
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String getMethod(){
		return this.method;
	}
	
	public String getUrl(){
		return this.url;
	}
	
	
	public Map<String,String> getParameter(){
		return null;
	}
	
}
