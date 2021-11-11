package com.enjoy.controller;

import com.alibaba.dubbo.rpc.RpcContext;
import com.enjoy.entity.OrderEntiry;
import com.enjoy.service.OrderService;
import com.enjoy.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Controller
public class OrderController {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String getDetail(HttpServletRequest request, HttpServletResponse response){
        OrderEntiry orderView = orderService.getDetail("1");

        request.setAttribute("order", orderView);
        return "order";
    }

    /**
     * 异步并发调用
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String cancel(HttpServletRequest request, HttpServletResponse response)  {
        OrderEntiry orderView = orderService.getDetail("1");

        String cancel_order = null,cancel_pay = null;
        long start = System.currentTimeMillis();

        //若设置了async=true，方法立即返回null
        cancel_order = orderService.cancel(orderView);
        //只有async=true，才能得到此对象，否则为null
        Future<String> cancelOrder = RpcContext.getContext().getFuture();
        cancel_pay = payService.cancelPay(orderView.getMoney());
        Future<String> cancelpay = RpcContext.getContext().getFuture();

        /**
         * Future模式
         *
         */

        try {
            cancel_order = cancelOrder.get();
            cancel_pay = cancelpay.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        request.setAttribute("cancelOrder", cancel_order);
        request.setAttribute("cancelpay", cancel_pay);

        long time = System.currentTimeMillis() - start;
        request.setAttribute("time", time);

        return "/cancel";
    }

    /**
     * 事件通知
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/order/submit", method = RequestMethod.GET)
    public String submit(HttpServletRequest request, HttpServletResponse response){
        OrderEntiry orderView = orderService.getDetail("1");
        orderView.setStatus(1);
        orderService.submit(orderView);

        request.setAttribute("order", orderView);
        return "/order";
    }

}
