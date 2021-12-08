package com.imooc.product.enums;

import lombok.Getter;

/**
 * 商品上下架状态
 * Created by 廖师兄
 * 2017-12-09 22:00
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架"),
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
