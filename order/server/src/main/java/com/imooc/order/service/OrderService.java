package com.imooc.order.service;

import com.imooc.order.DTO.OrderDTO;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(String orderId);
}
