package com.imooc.order.client;

import com.imooc.order.DTO.CartDto;
import com.imooc.order.model.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "product")
public interface ProductClient {
    @GetMapping("/msg")
    public String productMsg();

    @PostMapping("/product/getProductList")
    public List<ProductInfo> getProductList(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDto> cartDtoList);
}
