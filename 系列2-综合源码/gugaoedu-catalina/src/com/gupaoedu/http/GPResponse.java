package com.gupaoedu.http;

import java.io.OutputStream;

public class GPResponse {

	private OutputStream os;
	
	public GPResponse(OutputStream os){
		this.os = os;
	}
	
	public void write(String outstr) throws Exception{
		
		os.write(outstr.getBytes());
		os.flush();
	}
}
