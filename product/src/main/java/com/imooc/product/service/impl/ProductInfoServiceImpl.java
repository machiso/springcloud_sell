package com.imooc.product.service.impl;

import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.model.ProductInfo;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductInfoService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
