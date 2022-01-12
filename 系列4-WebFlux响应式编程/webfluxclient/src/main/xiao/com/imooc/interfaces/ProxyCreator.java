package com.imooc.interfaces;

/**
 * 创建代理类接口
 * 
 * @author 晓风轻
 *
 */
public interface ProxyCreator {
	/**
	 * 创建代理类
	 * 
	 * @param type
	 * @return
	 */
	Object createProxy(Class<?> type);
}
