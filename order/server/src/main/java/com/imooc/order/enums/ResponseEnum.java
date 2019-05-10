package com.imooc.order.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    PARAM_ERROR(1,"参数错误"),
    CART_EMPTY(2,"购物车为空"),
    LOGIN_FAIL(3,"登陆失败"),
    ROLE_ERROR(4,"角色权限有误"),
    ORDER_NOT_EXIST(5,"订单不存在"),
    ORDER_STATUS_ERROR(6,"订单状态错误"),
    ORDER_DETAIL_ERROR(7,"订单详情不存在"),
    ;

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
