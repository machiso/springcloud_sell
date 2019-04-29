package com.imooc.order.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class ProductInfo {
    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    // 商品状态,0正常1下架
    private Integer productStatus;
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;

}
