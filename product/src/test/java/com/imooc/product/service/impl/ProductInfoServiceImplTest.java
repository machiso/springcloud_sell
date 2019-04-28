package com.imooc.product.service.impl;

import com.imooc.product.ProductApplicationTests;
import com.imooc.product.model.ProductInfo;
import com.imooc.product.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class ProductInfoServiceImplTest extends ProductApplicationTests {

    @Autowired private ProductInfoService productInfoService;

    @Test
    public void findByProductIdIn() {
        List<ProductInfo> list = productInfoService.findByProductIdIn(Arrays.asList("164103465734242707"));
        Assert.assertTrue(list.size()>0);
    }
}