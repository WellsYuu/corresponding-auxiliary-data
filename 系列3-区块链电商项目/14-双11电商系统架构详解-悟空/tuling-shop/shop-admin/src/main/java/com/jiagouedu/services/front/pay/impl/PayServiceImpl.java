package com.jiagouedu.services.front.pay.impl;import com.jiagouedu.core.ServersManager;import com.jiagouedu.services.front.pay.PayService;import com.jiagouedu.services.front.pay.bean.Pay;import com.jiagouedu.services.front.pay.dao.PayDao;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import javax.annotation.Resource;@Servicepublic class PayServiceImpl extends ServersManager<Pay, PayDao> implements PayService {    @Resource(name = "payDaoFront")    @Override    public void setDao(PayDao payDao) {        this.dao = payDao;    }}