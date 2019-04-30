package com.imooc.product.service.impl;

import com.imooc.product.enums.ProductEnum;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.exception.ProductException;
import com.imooc.product.model.ProductInfo;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductInfoService;
import common.DecreaseStockInput;
import common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findByProductIdIn(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(productInfo -> {
                    ProductInfoOutput productInfoOutput = new ProductInfoOutput();
                    BeanUtils.copyProperties(productInfo,productInfoOutput);
                    return productInfoOutput;
                }).collect(Collectors.toList());
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> DecreaseStockInputList) {
        for (DecreaseStockInput decreaseStockInput:DecreaseStockInputList){
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
            //商品不存在
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ProductEnum.PRODUCT_EMPTY);
            }
            ProductInfo productInfo = productInfoOptional.get();
            int sub = productInfo.getProductStock()-decreaseStockInput.getProductQuantity();
            if (sub<0){
                throw new ProductException(ProductEnum.PRODUCT_NOT_EMPTY);
            }
            productInfo.setProductStock(sub);
            productInfoRepository.save(productInfo);
        }
    }
}
