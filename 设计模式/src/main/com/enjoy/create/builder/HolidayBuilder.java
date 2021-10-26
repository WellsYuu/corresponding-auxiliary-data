package com.enjoy.create.builder;

import com.enjoy.entity.fruit.Apple;
import com.enjoy.entity.fruit.Banana;
import com.enjoy.entity.fruit.Orange;

/**
 * 桔子
 * Created by Peter on 10/9 009.
 */
public class HolidayBuilder implements Builder {
    private FruitMeal fruitMeal = new FruitMeal();

    @Override
    public void buildApple(int price) {
        Apple apple = new Apple();
        apple.setPrice(price);
        fruitMeal.setApple(apple);
    }

    @Override
    public void buildBanana(int price) {
        Banana fruit = new Banana();
        fruit.setPrice(price);
        fruitMeal.setBanana(fruit);
    }

    @Override
    public void buildOrange(int price) {
        Orange fruit = new Orange("Peter",80);
        fruit.setPrice(price);
        fruitMeal.setOrange(fruit);
    }

    @Override
    public FruitMeal getFruitMeal() {
        fruitMeal.setDiscount(15);//折扣价格对一个套餐来，是固定的
        fruitMeal.init();
        return fruitMeal;
    }
}
