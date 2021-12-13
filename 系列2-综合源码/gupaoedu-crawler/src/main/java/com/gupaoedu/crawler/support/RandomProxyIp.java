package com.gupaoedu.crawler.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 随机获取一个ip地址
 * @author Tom
 *
 */
public class RandomProxyIp {
	private static Log log = LogFactory.getLog(RandomProxyIp.class);
	// 随机类,用于产生随机数
	protected static Random random = new Random();
	private static List<String> proxyIpList = new ArrayList<String>();
	static {
		try{
			String path  = ClassLoader.getSystemResource("").getPath().replaceAll("/test-classes", "/classes");
			File file = new File(path + "proxy.txt");
			if(file.exists()){
				InputStream in = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				while(br.ready()){
					proxyIpList.add(br.readLine().trim());
				}
				in.close();
				br.close();
			}
		}catch(Exception e){
			log.error(e);
		}
	}
	
	/**
	 * 随机产生代理IP
	 * @return
	 */
	public static String randomProxyIp(){
		if(null == proxyIpList || proxyIpList.size() == 0){return "";}
		
		int index = random.nextInt(proxyIpList.size());
		return proxyIpList.get(index);
	}
}
