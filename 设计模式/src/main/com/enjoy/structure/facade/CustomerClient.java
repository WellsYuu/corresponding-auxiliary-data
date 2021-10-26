package com.enjoy.structure.facade;

/**
 * 客户服务一条龙
 */
public class CustomerClient {


    public static void main(String[] args){
        doOrderFacade();
    }

    public static void doOrder(){

        PickService pickService = new PickService();
        PackService packService = new PackService();
        SendService sendService = new SendService();

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

    public static void doOrderFacade(){
        OrderFacade orderFacade = new OrderFacade();
        orderFacade.doOrder();
    }


}
