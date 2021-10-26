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


//音箱类
public class SpeakerBox implements Item {
    private String name;
    private float price;

    public SpeakerBox(String name,int price){
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

