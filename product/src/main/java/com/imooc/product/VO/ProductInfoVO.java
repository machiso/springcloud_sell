package com.imooc.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductInfoVO {
    @JsonProperty("name")
    private String productName;

    @JsonProperty("type")
    private Integer categoryType;
}
