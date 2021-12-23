package com.edu.example.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.edu.example.bean.Order;
import com.edu.example.common.util.HttpClientUtils;
import com.edu.example.common.util.RespEntity;
import com.edu.example.service.BankService;

/**
 * 第三方请求接口
 * @author 张飞老师
 */
@Component
public class BankServiceImpl implements BankService {

    private static Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);
    private static final String MONEY_OUT_URL = "http://ip:port/bank/outMoney";

    /***
     * 第三方请求接口
     * @param url
     * @param requestBody
     */
	@SuppressWarnings("unused")
	public RespEntity<Object> outMoney(Order order) {
		try {
			String requestBody = JSON.toJSONString(order);
			logger.info(order.toString());
	        Map<String, String> headers = new HashMap<>();
	        String response = HttpClientUtils.httpPostJson(MONEY_OUT_URL, headers, requestBody, 5000);
	        // 解析response ，通过json格式获取银行返回的状态码和消息
	        // 然后转换为我们自己的响应体，返回
	        RespEntity<Object> respEntity = new RespEntity<Object>();
	        respEntity.setKey("0000");// 成功
	        return respEntity;
		} catch (Exception e) {
			return null;
		}
    }
}
