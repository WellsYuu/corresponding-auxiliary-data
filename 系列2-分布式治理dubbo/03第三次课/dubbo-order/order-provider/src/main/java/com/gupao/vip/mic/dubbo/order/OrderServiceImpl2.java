package com.gupao.vip.mic.dubbo.order;

import com.gupao.vip.mic.dubbo.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("orderService2")
public class OrderServiceImpl2 implements IOrderServices{

 /*   @Autowired
    IUserService userService;*/

    @Override
    public DoOrderResponse doOrder(DoOrderRequest request) {
        System.out.println("曾经来过版本2："+request);
        DoOrderResponse response=new DoOrderResponse();
        response.setCode("000000");
        response.setMemo("处理成功，版本2");
        return response;
    }
}
