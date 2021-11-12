package com.enjoy.structure.facade;

/**
 * 客户服务一条龙
 */
public class OrderFacade {
    private PickService pickService;
    private PackService packService;
    private SendService sendService;


    public OrderFacade(){
        pickService = new PickService();
        packService = new PackService();
        sendService = new SendService();
    }

    /**
     * 客户订单处理类
     */
    public void doOrder(){
        //采摘
        System.out.println("--------------");
        pickService.doPick();

        //包装
        System.out.println("--------------");
        packService.doPack();
        //快递
        System.out.println("--------------");
        sendService.doSend();

    }



}
