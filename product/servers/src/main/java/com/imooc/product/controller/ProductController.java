package com.imooc.product.controller;

import com.imooc.product.VO.ProductInfoVO;
import com.imooc.product.VO.ProductVO;
import com.imooc.product.VO.Response;
import com.imooc.product.VO.ResponseUtil;
import com.imooc.product.model.ProductCategory;
import com.imooc.product.model.ProductInfo;
import com.imooc.product.service.ProductCategoryService;
import com.imooc.product.service.ProductInfoService;
import common.DecreaseStockInput;
import common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired private ProductInfoService productInfoService;
    @Autowired private ProductCategoryService productCategoryService;
    /**
     * 查询所有在线商品列表
     * 1 查询所有在架的商品
     * 2 获取商品类目type列表
     * 3 根据type查询商品类目
     * 4 构造数据
     */

    @GetMapping("/list")
    public Response<ProductVO> list(){
        //1 查询所有在架的商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        //2 获取商品类目type列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
        //3 根据type查询商品类目
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        //4 构造数据
        List<ProductVO> productVOS = new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOS);
            productVOS.add(productVO);
        }

        return ResponseUtil.success(productVOS);
    }

    /**
     * 查询商品信息（提供order服务调用）
     */
    @PostMapping("/getProductList")
    public List<ProductInfoOutput> getProductList(@RequestBody List<String> productIdList){
        return productInfoService.findByProductIdIn(productIdList);
    }

    /**
     * 扣库存(提供订单服务调用)
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList){
        productInfoService.decreaseStock(decreaseStockInputList);
    }
}
