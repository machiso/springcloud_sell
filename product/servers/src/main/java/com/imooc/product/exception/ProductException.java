package com.imooc.product.exception;

import com.imooc.product.enums.ProductEnum;

public class ProductException extends RuntimeException {

    private Integer code;

    public ProductException(Integer code,String message){
        super(message);
        this.code=code;
    }

    public ProductException(ProductEnum productEnum){
        super(productEnum.getMessage());
        this.code = productEnum.getCode();
    }
}
