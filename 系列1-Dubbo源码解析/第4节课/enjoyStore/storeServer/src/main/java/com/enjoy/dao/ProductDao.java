package com.enjoy.dao;

import com.enjoy.entity.ProductEntiry;

public interface ProductDao {
    ProductEntiry getDetail(String id);
    ProductEntiry modify(ProductEntiry product);
    boolean status(String id, boolean upDown);
}
