package com.enjoy.callback;

import com.enjoy.entity.OrderEntiry;

/**
 * Created by Peter on 11/13 013.
 */
public class CallBack {

    //第一个参数，为返回结果值，后续参数是入参
    public void onOrderSubmit(OrderEntiry result,OrderEntiry form){
        System.out.println("生成了一单，金额："+ result.getMoney());
    }

    //第一个参数，为返回结果值，后续参数是入参
    public void onError(Throwable ex,OrderEntiry form){

        System.out.println("生成订单异常，请紧急处理,订单金额："+ form.getMoney());
        System.out.println(ex.getMessage());
    }
}
