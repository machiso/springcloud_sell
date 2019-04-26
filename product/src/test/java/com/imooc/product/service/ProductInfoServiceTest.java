package com.imooc.product.service;

import com.imooc.product.ProductApplication;
import com.imooc.product.ProductApplicationTests;
import com.imooc.product.model.ProductInfo;
import com.imooc.product.repository.ProductInfoRepository;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.Assert.*;

@Component
public class ProductInfoServiceTest extends ProductApplicationTests {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findUpAll() {
        List<ProductInfo> list = productInfoService.findUpAll();
        System.out.println(list);
    }
}