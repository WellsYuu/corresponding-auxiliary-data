package com.gupao.vip.mic.dubbo.order;

import com.gupao.vip.mic.dubbo.dal.OrderDao;
import com.gupao.vip.mic.dubbo.user.IUserService;
import com.gupao.vip.mic.dubbo.user.dto.DebitRequest;
import com.gupao.vip.mic.dubbo.user.dto.DebitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service(value = "orderService")
public class OrderServiceImpl implements IOrderServices{

    @Autowired
    JtaTransactionManager springTransactionManager;

    @Autowired
    OrderDao orderDao;

    @Autowired
    IUserService userService;

    public DoOrderResponse doOrder(DoOrderRequest request) {
        DoOrderResponse response=new DoOrderResponse();
        //order下单后调用user更新余额
        UserTransaction userTransaction=springTransactionManager.getUserTransaction();
        try {
            userTransaction.begin();
            orderDao.insertOrder();
            DebitResponse response1 = userService.debit(new DebitRequest());
            userTransaction.commit();
        }catch(Exception e){
            try {
                userTransaction.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
        response.setCode("000000");
        return response;
    }
}
