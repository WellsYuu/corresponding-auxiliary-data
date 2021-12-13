package com.gupaoedu.crawler.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数工具类
 * @author Tom
 *
 */
public class Param {
	
	protected Map<String,Object> dataMap = new HashMap<String,Object>();
	
	public Param data(String key,Object value){
		this.dataMap.put(key, value);
		return this;
	}

	/* 获取拼接参数后的url
	 * @param basePath
	 * @return
	 */
	public String getUrl(String basePath){
		StringBuffer sb = new StringBuffer();
		if(!(null == basePath || "".equals(basePath.trim())) || -1 != basePath.indexOf("\\?")){
			sb.append(basePath);
			sb.append("?");
		}
		int i = 0;
		for (String key : dataMap.keySet()) {
			if(i > 0 && i < dataMap.size()){
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(dataMap.get(key));
			i ++;
		}
		return sb.toString();
	}
}
