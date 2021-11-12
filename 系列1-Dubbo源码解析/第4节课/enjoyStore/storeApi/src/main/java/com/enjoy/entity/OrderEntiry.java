package com.enjoy.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 11/12 012.
 */
public class OrderEntiry  implements Serializable {
    private String id;
    private long money;
    private String userId;
    private int status = 0;
    private List<ProductEntiry> productlist = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public List<ProductEntiry> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<ProductEntiry> productlist) {
        this.productlist = productlist;
    }

    public void addProduct(ProductEntiry productEntiry){
        productlist.add(productEntiry);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
