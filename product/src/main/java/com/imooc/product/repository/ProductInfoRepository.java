package com.imooc.product.repository;

import com.imooc.product.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * order服务调用 product服务
     * @return
     */
    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
