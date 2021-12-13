package com.gupaoedu.crawler.service.impl;

import javax.core.common.Page;
import javax.core.common.ResultMsg;

import org.springframework.stereotype.Service;

import com.gupaoedu.crawler.model.House;
import com.gupaoedu.crawler.parser.house.LianjiaParser;
import com.gupaoedu.crawler.service.IHouseSearchService;

@Service
public class HouseSearchService implements IHouseSearchService{

	@Override
	public ResultMsg<?> search(String keyword,int pageNo,int pageSize) {
		ResultMsg<Page<?>> result = new ResultMsg<Page<?>>();
		try {
			Page<House> page = new LianjiaParser().parse(keyword, pageNo, pageSize);
			result.setData(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
