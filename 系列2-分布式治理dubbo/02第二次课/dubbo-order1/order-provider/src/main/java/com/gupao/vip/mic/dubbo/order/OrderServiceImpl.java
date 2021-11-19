package com.gupao.vip.mic.dubbo.order;

import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service(value = "orderService")
public class OrderServiceImpl implements IOrderServices{


    public DoOrderResponse doOrder(DoOrderRequest request) {
        System.out.println("曾经来过："+request);
        DoOrderResponse response=new DoOrderResponse();
        response.setCode("000000");
        response.setMemo("处理成功");
        return response;
    }
}
