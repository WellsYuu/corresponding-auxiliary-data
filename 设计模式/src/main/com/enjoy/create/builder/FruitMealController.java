package com.enjoy.create.builder;

/**
 * 桔子
 * Created by Peter on 10/9 009.
 */
public class FruitMealController {//收银台---导演类

    public void construct() {
//        Builder builder = new HolidayBuilder();
        Builder builder = new OldCustomerBuilder();//spring注入方法，

        //以下代码模板，轻易是不变的
        builder.buildApple(120);//创建苹果设置价格
        builder.buildBanana(80);//创建香蕉设置香蕉价格
        builder.buildOrange(50);//创建桔子设置价格

        FruitMeal fruitMeal = builder.getFruitMeal();


        int cost = fruitMeal.cost();
        System.out.println("本套件花费："+cost);
    }

    public static void main(String[] args) {
        new FruitMealController().construct();
    }

}
