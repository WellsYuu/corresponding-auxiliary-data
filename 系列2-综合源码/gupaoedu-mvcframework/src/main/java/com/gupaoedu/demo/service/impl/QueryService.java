package com.gupaoedu.demo.service.impl;

import com.gupaoedu.demo.service.IQueryService;
import com.gupaoedu.mvcframework.annotation.GPService;

@GPService
public class QueryService implements IQueryService {

	@Override
	public String search(String name) {
		return "invork search name = " + name;
	}

}
