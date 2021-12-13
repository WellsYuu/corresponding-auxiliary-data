package com.gupaoedu.servlet;

import com.gupaoedu.http.GPRequest;
import com.gupaoedu.http.GPResponse;
import com.gupaoedu.http.GPServlet;

public class SecondServlet extends GPServlet{

	@Override
	public void doGet(GPRequest request, GPResponse response) throws Exception{
		this.doPost(request, response);
	}

	@Override
	public void doPost(GPRequest request, GPResponse response) throws Exception {
		
		//在这里处理自己所有的逻辑
		response.write("Hello Second Serlvet");
		
	}

}
