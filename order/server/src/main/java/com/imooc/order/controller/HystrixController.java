package com.imooc.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class HystrixController {

//    @Autowired private RestTemplate restTemplate;

    //List<ProductInfoOutput> productList = productClient.getProductList(productIdList);

    @GetMapping("/getProductInfoList")
    @HystrixCommand(fallbackMethod = "fallback")
    public String getProductList(){
        RestTemplate restTemplate = new RestTemplate();
       return restTemplate.postForObject("http://127.0.0.1:8081/product/getProductList",
                Arrays.asList("157875196366160022"),String.class);
    }

    public String fallback(){
        return "前方拥挤，请稍等....";
    }
}
