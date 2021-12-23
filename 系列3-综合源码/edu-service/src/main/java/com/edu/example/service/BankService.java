package com.edu.example.service;

import com.edu.example.bean.Order;
import com.edu.example.common.util.RespEntity;


/**
 * 银行Service
 * 
 * @author 张飞老师
 */
public interface BankService {

	/**
	 * 扣款
	 */
    RespEntity<Object> outMoney(Order order);
}
