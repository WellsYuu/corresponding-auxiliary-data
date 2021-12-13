package com.gupaoedu.http;

public abstract class GPServlet {

	public void service(GPRequest request,GPResponse response) throws Exception{
		
		//如果客户端发送的是GET请求，就调用doGet方法
		//如果是POST,调用doPost方法
		
		if("GET".equalsIgnoreCase(request.getMethod())){
			doGet(request, response);
		}else{
			doPost(request, response);
		}
	}
	
	public abstract void doGet(GPRequest request,GPResponse response) throws Exception;
	
	public abstract void doPost(GPRequest request,GPResponse response) throws Exception;
	
}
