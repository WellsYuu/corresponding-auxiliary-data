package com.jiagouedu.core;

import com.jiagouedu.services.front.keyvalue.bean.Keyvalue;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.Map.Entry;


/**
 * key-value工具类，页面获取中文文字描述都从此获取
 * 
 * @author wukong 图灵学院 QQ:245553999ei
 * 
 */
public class KeyValueHelper {
	private static Map<String, String> map = Collections
			.synchronizedMap(new HashMap<String, String>());

	public static void load(List<Keyvalue> list) {
		if (list == null || list.size() == 0) {
			map.clear();
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			Keyvalue keyvalue = list.get(i);
			map.put(keyvalue.getKey1(), keyvalue.getValue());
		}
	}
	
	/**
	 * 根据指定的key获取指定的value
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		if (StringUtils.isEmpty(key)) {
			throw new NullPointerException("key is not null.");
		}

		return map.get(key);
	}
	
	/**
	 * key的规则是：table_column_key
	 * @param key  参数为table_column_
	 * @return
	 */
	public static Map<String,String> getMap(String key) {
		if (StringUtils.isEmpty(key)) {
			throw new NullPointerException("key is not null.");
		}
		Map<String, String> mapItem = new HashMap<String, String>();
		for(Iterator<Entry<String, String>> it = map.entrySet().iterator();it.hasNext();){
			Entry<String, String> entry = it.next();
			if(entry.getKey().indexOf(key)>=0){
				mapItem.put(entry.getKey(), entry.getValue());
			}
		}
		return mapItem;
	}
}
