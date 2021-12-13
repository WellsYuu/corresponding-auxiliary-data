package com.gupaoedu.crawler.service;

import javax.core.common.ResultMsg;

public interface IHouseSearchService {

	ResultMsg<?> search(String keyword,int pageNo,int pageSize); 
	
}
