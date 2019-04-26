package com.imooc.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/product")
public class ProductController {

    /**
     * 查询所有在线商品列表
     * 1 查询所有在架的商品
     * 2 获取商品类目type列表
     * 3 根据type查询商品类目
     * 4 构造数据
     */

    @GetMapping("/list")
    public void list(){

    }
}
