package com.gupaoedu.crawler.mvc.action;

import javax.core.common.ResultMsg;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.core.mvc.action.BaseAction;
import com.gupaoedu.crawler.service.IHouseSearchService;

@Controller
@RequestMapping("/web/house")
public class HouseSearchAction extends BaseAction{
	
	@Autowired IHouseSearchService houseSearchService;
	
	@RequestMapping("/search.json")
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("keyword") String keyword,
			@RequestParam(value="pageNo",required=false,defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize){
		ResultMsg<?> result = houseSearchService.search(keyword, pageNo, pageSize);
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
}
