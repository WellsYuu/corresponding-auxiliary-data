package com.enjoy.service;

import com.enjoy.entity.ProductEntiry;

public interface ProductService {
    ProductEntiry getDetail(String id);
    ProductEntiry modify(ProductEntiry product);
    boolean status(String id, boolean upDown);
}
