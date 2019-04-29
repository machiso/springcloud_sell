package com.imooc.order.controller;

import com.imooc.order.client.ProductClient;
import com.imooc.order.model.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import com.imooc.order.DTO.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

    @Autowired private ProductClient productClient;

    @GetMapping("getProductMsg")
    public String getProductMsg(){
        String msg = productClient.productMsg();
        log.info("msg = {}",msg);
        return msg;
    }

    /**
     * 获取商品列表（给订单）
     */
    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfo> productList = productClient.getProductList(Arrays.asList("164103465734242707"));
        log.info("response={}",productList);
        return "ok";
    }

    @GetMapping("/decreaseStock")
    public String decreaseStock(){
        CartDto cartDto = new CartDto("164103465734242707",3);
        productClient.decreaseStock(Arrays.asList(cartDto));
        return "ok";
    }

}
