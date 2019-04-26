package com.imooc.product.service;

import com.imooc.product.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);
}
