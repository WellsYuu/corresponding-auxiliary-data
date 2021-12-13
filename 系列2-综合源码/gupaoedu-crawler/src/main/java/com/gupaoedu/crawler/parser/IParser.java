package com.gupaoedu.crawler.parser;

import javax.core.common.Page;

/**
 * �ṩ���ص��������õ�ͨ�ýӿ�
 * @author Tom
 *
 */
public interface IParser {
	/**
	 * �����ӻ�������ץȡ�Ľ��
	 * @param keyword
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<?> parse(String keyword,int pageNo,int pageSize) throws Exception;
}
