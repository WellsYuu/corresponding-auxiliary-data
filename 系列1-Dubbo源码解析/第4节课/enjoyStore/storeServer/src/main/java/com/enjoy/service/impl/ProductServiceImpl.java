package com.enjoy.service.impl;

import com.enjoy.dao.ProductDao;
import com.enjoy.entity.ProductEntiry;
import com.enjoy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class ProductServiceImpl implements ProductService {
    private static Map<String,ProductEntiry> productMap = new HashMap<>();
    static {
        ProductEntiry product = new ProductEntiry();
        product.setId("P001");
        product.setName("iponex");
        product.setPrice(10000);
        productMap.put(product.getId(),product);

        product = new ProductEntiry();
        product.setId("P002");
        product.setName("大疆无人机");
        product.setPrice(100000);
        productMap.put(product.getId(),product);
    }

    @Autowired
    private ProductDao productDao;

    @Override
    public ProductEntiry getDetail(String id) {
        ProductEntiry product = productMap.get(id);
        if (null != product){
            return product;
        }
        System.out.println(super.getClass().getName()+"被调用一次："+System.currentTimeMillis());
        return productDao.getDetail(id);
    }

    @Override
    public ProductEntiry modify(ProductEntiry product) {
        return productDao.modify(product);
    }

    @Override
    public boolean status(String id, boolean upDown) {
        return productDao.status(id,upDown);
    }
}
