package com.imooc.order.controller;

import com.imooc.order.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ClientController {

    @Autowired private ProductClient productClient;

    @GetMapping("getProductMsg")
    public String getProductMsg(){
        String msg = productClient.productMsg();
        log.info("msg = {}",msg);
        return msg;
    }

}
