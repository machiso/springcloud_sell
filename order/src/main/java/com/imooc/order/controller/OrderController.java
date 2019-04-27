package com.imooc.order.controller;

import com.imooc.order.form.OrderForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * 1 参数校验
     * 2 查询商品信息（调用商品服务）
     * 3 计算总价
     * 4 扣库存（调用商品服务）
     * 5 订单入库
     * @return
     */
    @PostMapping("/create")
    public String create(@Valid OrderForm orderForm){
//        TODO
//        if (orderForm)
        return null;
    }

}
