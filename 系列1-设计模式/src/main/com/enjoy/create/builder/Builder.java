package com.enjoy.create.builder;


//创建一个MealBuilder类，实际的builder类负责创建套餐Meal对象。
public interface Builder {//也是工厂

    void buildApple(int price);//设置苹果
    void buildBanana(int price);//设置香蕉
    void buildOrange(int price);//设置桔子

    FruitMeal getFruitMeal();//返回创建的套餐
}

