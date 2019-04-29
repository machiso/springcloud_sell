package com.imooc.product.dto;

import lombok.Data;

@Data
public class CartDto {

    private String productId;
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDto(){};
}
