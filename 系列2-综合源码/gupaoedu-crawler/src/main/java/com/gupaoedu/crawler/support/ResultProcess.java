package com.gupaoedu.crawler.support;

import java.io.IOException;
import java.io.InputStream;

import javax.core.common.config.CustomConfig;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


/**
 * 请求互联网,获取返回结果的输入流
 * @author Tom
 *
 */
public class ResultProcess {

	/**
	 * 发送http请求,获取返回过来的流
	 * @param url
	 * @param conTimeoutMill 连接超时时间
	 * @param soTimeoutMill 返回结果超时时间
	 * 
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static InputStream httpStream(String url,int conTimeoutMill,int soTimeoutMill){
		InputStream in = null;
		HttpClient httpClient = null;
		//获得客户端
		if(CustomConfig.getBoolean("system.proxy")){
			httpClient = getProxyClient();
		}else{
			httpClient = new HttpClient();
		}
		//设置请求超时时间
		if(conTimeoutMill > 0 ){
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(conTimeoutMill); //连接超时时间（毫秒）
		}
		if(soTimeoutMill > 0 ){
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeoutMill); //获取返回结果超时时间（毫秒）
		}
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter("http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());//使用系统提供的默认的恢复策略
		
		//获取结果
		try{
			int stat = httpClient.executeMethod(getMethod);
			if (stat == HttpStatus.SC_OK) {
				in = getMethod.getResponseBodyAsStream();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return in;
	}
	
	/**
	 * 获取一个代理客户端
	 * @return
	 */
	public static HttpClient getProxyClient(){
		HttpClient httpClient = new HttpClient();
		//设置代理IP
		String host = RandomProxyIp.randomProxyIp();
		if(null != host && !"".equals(host.trim())){
			String [] ip = host.split("\\:");
			httpClient.getHostConfiguration().setProxy(ip[0], Integer.valueOf(ip[1]));
		}
		return httpClient;
	}
	
}
