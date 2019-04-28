package com.imooc.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "product")
public interface ProductClient {
    @GetMapping("/msg")
    public String productMsg();

    @PostMapping("/product/getProductList")
    public String getProductList();
}
