package com.imooc.interfaces;

import com.imooc.beans.MethodInfo;
import com.imooc.beans.ServerInfo;

/**
 * rest请求调用handler
 * 
 * @author 晓风轻
 *
 */
public interface RestHandler {

	/**
	 * 初始化服务器信息
	 * 
	 * @param serverInfo
	 */
	void init(ServerInfo serverInfo);

	/**
	 * 调用rest请求, 返回接口
	 * 
	 * @param methodInfo
	 * @return
	 */
	Object invokeRest(MethodInfo methodInfo);

}
