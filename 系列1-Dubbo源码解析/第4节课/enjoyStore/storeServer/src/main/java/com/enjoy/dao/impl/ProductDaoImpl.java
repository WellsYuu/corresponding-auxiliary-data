package com.enjoy.dao.impl;

import com.enjoy.dao.ProductDao;
import com.enjoy.entity.ProductEntiry;
import org.springframework.stereotype.Service;

@Service
public class ProductDaoImpl implements ProductDao {
    @Override
    public ProductEntiry getDetail(String id) {
        ProductEntiry product = new ProductEntiry();
        product.setId("P003");
        product.setName("华为meta10");
        product.setPrice(5000);
        return product;
    }

    @Override
    public ProductEntiry modify(ProductEntiry product) {
        product = new ProductEntiry();
        product.setId("P004");
        product.setName("小米机器人");
        product.setPrice(2000);
        return product;
    }

    @Override
    public boolean status(String id, boolean upDown) {
        System.out.println("商品上下架成功");
        return true;
    }

}
