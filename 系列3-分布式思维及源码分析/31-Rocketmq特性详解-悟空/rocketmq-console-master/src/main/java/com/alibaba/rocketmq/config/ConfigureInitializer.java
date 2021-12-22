package com.alibaba.rocketmq.config;


import java.util.List;

import com.alibaba.rocketmq.common.MixAll;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;


/**
 * 把需要补充的初始化环境变量正式的放入系统属性中
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-10
 * 
 */
public class ConfigureInitializer {
	
	private String users;
	
	/**
	 * 登录用户
	 */
	public ImmutableMap<String, String> userMap; 
	
	{

	}
    
    private String namesrvAddr;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }
    
    public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public void init() {
        System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, namesrvAddr);
        //构建登录用户名单
		Builder<String, String> builder = ImmutableMap.<String, String>builder();
		
		Iterable<String> userIt = Splitter.on(";").trimResults().omitEmptyStrings().split(users);
		for(String user : userIt) {
			List<String> list = Splitter.on(":").trimResults().omitEmptyStrings().splitToList(user);
			String phone = list.get(0);
			String password = list.get(1);
			builder.put(phone, password);
		}
		userMap = builder.build();
    }
}
