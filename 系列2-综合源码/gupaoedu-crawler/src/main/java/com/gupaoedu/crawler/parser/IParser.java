package com.gupaoedu.crawler.parser;

import javax.core.common.Page;

/**
 * 提供给回调函数调用的通用接口
 * @author Tom
 *
 */
public interface IParser {
	/**
	 * 解析从互联网上抓取的结果
	 * @param keyword
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<?> parse(String keyword,int pageNo,int pageSize) throws Exception;
}
