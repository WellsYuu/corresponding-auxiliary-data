package com.gupaoedu.catalina;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

import com.gupaoedu.http.GPRequest;
import com.gupaoedu.http.GPResponse;
import com.gupaoedu.http.GPServlet;
import com.gupaoedu.servlet.FirstServlet;

public class GPTomcat {
	
	//必要条件
	//端口号，默认8080
	//web.xml是必须要的
	private int port = 8080;
	
	private ServerSocket server;
	
	private Map<Pattern,Class<?>> servletMapping = new HashMap<Pattern,Class<?>>();
	
	private Properties webxml = new Properties();
	
	private String WEB_INF = this.getClass().getResource("/").getPath();
	
	
	//WEB容器的基本架构就起来了
	public GPTomcat(){
		
	}
	
	public GPTomcat(int port){
		this.port = port;
	}
	
	//在容器启动之前，要加载所有的配置文件
	private void init(){
		
		FileInputStream fis = null;
		try{
			
			fis = new FileInputStream(WEB_INF + "web.properties");
			
			webxml.load(fis);
			
			for (Object k : webxml.keySet()) {
				String key = k.toString();
				if(key.endsWith(".url")){
					String servletName = key.replaceAll("\\.url$", "");
					String url = webxml.getProperty(key);
					
					Pattern pattern = Pattern.compile(url);
					
					String className = webxml.getProperty(servletName + ".className");
					
					Class<?> servletClass = Class.forName(className);
					
					servletMapping.put(pattern , servletClass);
					
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//等待用户请求了
	private void process(Socket client) throws Exception{
		//首先要把Request和Respose搞出来
		//Request 实际上就是对我们的InputStream的一个封装
		//Respose OutputStream的封装
		InputStream is = client.getInputStream();
		OutputStream out = client.getOutputStream();
		
		GPRequest request = new GPRequest(is);
		
		GPResponse response = new GPResponse(out);
		
		//
		try{
			
			//此时此刻，还缺一个Serlvet
			//service(Request,Reponse)　　　　　doGet doPost

			//这个Servlet自己从来没有亲手new过？
			//通过读取web.xml 文件来获取自己的servlet
			//利用反射机制给new出来
			
			String url = request.getUrl();
			
			boolean isPattern = false;
			
			for (Entry<Pattern, Class<?>> entry : servletMapping.entrySet()) {
				
				if(entry.getKey().matcher(url).matches()){
					
					GPServlet servlet = (GPServlet)entry.getValue().newInstance();
					servlet.service(request, response);
					
					isPattern = true;
					break;
				}
				
			}
			
			if(!isPattern){
				response.write("404 - Not Found");
			}
			
		}catch(Exception e){
			response.write("500 ," + e.getMessage());
		}finally{
			is.close();
			out.close();
			//HTTP本身是无状态的协议
			client.close();
		}
		
	}
	
	
	public void start(){
		
		init();
		
		//BIO的写法,现在新的tomcat已经支持nio了
		try {
			server = new ServerSocket(this.port);
			
			System.out.println("GP Tomcat 已启动，监听端口是：" +  this.port);
			
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("GP Tomcat 启动失败，" + e.getMessage());
			return;
		}
		
		while(true){
			
			//容器能够持续提供服务了
			try {
				Socket client = server.accept();
				
				process(client);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		new GPTomcat ().start();
	}
	
}
