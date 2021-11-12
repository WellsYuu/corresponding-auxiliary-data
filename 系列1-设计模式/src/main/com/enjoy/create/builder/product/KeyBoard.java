/**
 * <html>
 * <body>
 *  <P> Copyright 1994 JsonInternational</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 19941115</p>
 *  <p> Created by Jason</p>
 *  </body>
 * </html>
 */
package com.enjoy.create.builder.product;


//键盘类
public class KeyBoard implements Item{
    private String name;
    private float price;

    public KeyBoard(String name,int price){
        this.name = name;
        this.price = price;
    }

    public String name(){
        return name;
    }
    public float price(){
        return price;
    }

}

