package com.enjoy.entity;

import java.io.Serializable;

/**
 * Created by Peter on 11/12 012.
 */
public class ProductEntiry implements Serializable{


    private String id;
    private long price;
    private String name;
    private int status;//上下架

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
