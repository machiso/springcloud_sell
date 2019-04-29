package com.imooc.order.exception;

import com.imooc.order.enums.ResponseEnum;

public class OrderException extends RuntimeException{
    private Integer code;

    public OrderException(Integer code,String message){
        super(message);
        this.code=code;
    }

    public OrderException(ResponseEnum responseEnum){
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
