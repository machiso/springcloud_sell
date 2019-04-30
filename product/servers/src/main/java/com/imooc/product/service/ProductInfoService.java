package com.imooc.product.service;

import com.imooc.product.model.ProductInfo;
import common.DecreaseStockInput;
import common.ProductInfoOutput;

import java.util.List;

public interface ProductInfoService {
    //查询所有在线商品
    List<ProductInfo> findUpAll();

    List<ProductInfoOutput> findByProductIdIn(List<String> productIdList);

    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
