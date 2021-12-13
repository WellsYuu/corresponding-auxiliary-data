package com.gupaoedu.crawler.service.test;

import javax.core.common.ResultMsg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.crawler.service.IHouseSearchService;


@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class HouseSearchServiceTest {

	@Autowired IHouseSearchService houseSearchService;
	
	
	@Test
	public void testSearch(){
		ResultMsg<?> result = houseSearchService.search("Tom", 1, 10);
		System.out.println(JSON.toJSONString(result,true));
	}
	
}
