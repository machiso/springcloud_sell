package com.imooc.product.service;

import com.imooc.product.model.ProductInfo;

import java.util.List;

public interface ProductInfoService {
    //查询所有在线商品
    List<ProductInfo> findUpAll();

    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
