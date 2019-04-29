package com.imooc.product.enums;

import lombok.Getter;

@Getter
public enum  ProductEnum {
    PRODUCT_EMPTY(1,"商品不存在"),
    PRODUCT_NOT_EMPTY(2,"商品余额不足"),
    ;
    private Integer code;
    private String message;

    ProductEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
