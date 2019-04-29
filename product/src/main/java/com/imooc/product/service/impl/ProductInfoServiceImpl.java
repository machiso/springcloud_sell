package com.imooc.product.service.impl;

import com.imooc.product.dto.CartDto;
import com.imooc.product.enums.ProductEnum;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.exception.ProductException;
import com.imooc.product.model.ProductInfo;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductInfoService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findByProductIdIn(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto:cartDtoList){
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDto.getProductId());
            //商品不存在
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ProductEnum.PRODUCT_EMPTY);
            }
            ProductInfo productInfo = productInfoOptional.get();
            int sub = productInfo.getProductStock()-cartDto.getProductQuantity();
            if (sub<0){
                throw new ProductException(ProductEnum.PRODUCT_NOT_EMPTY);
            }
            productInfo.setProductStock(sub);
            productInfoRepository.save(productInfo);
        }
    }
}
