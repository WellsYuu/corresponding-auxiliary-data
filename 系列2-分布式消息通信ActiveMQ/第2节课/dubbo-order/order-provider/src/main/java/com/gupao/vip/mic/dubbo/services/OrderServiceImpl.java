package com.gupao.vip.mic.dubbo.services;

import com.gupao.vip.mic.dubbo.dal.persistence.OrderMapper;
import com.gupao.vip.mic.dubbo.order.dto.DoOrderRequest;
import com.gupao.vip.mic.dubbo.order.dto.DoOrderResponse;
import com.gupao.vip.mic.dubbo.order.IOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service(value = "orderService")
public class OrderServiceImpl implements IOrderServices {

    @Autowired
    JtaTransactionManager springTransactionManager;

    @Autowired
    OrderMapper orderMapper;


    public DoOrderResponse doOrder(DoOrderRequest request) {
        DoOrderResponse response=new DoOrderResponse();
        response.setCode("000000");
        return response;
    }
}
