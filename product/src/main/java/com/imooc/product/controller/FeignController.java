package com.imooc.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @GetMapping("/msg")
    public String msg(){
        return "hello msg";
    }
}
