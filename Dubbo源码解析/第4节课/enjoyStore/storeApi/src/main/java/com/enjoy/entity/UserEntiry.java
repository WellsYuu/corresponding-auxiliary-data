package com.enjoy.entity;

import java.io.Serializable;

/**
 * Created by Peter on 11/12 012.
 */
public class UserEntiry  implements Serializable {
    private String id;
    private String name;
    private String address;
    private long balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
