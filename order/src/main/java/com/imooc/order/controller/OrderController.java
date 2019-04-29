package com.imooc.order.controller;

import com.imooc.order.DTO.OrderDTO;
import com.imooc.order.VO.Response;
import com.imooc.order.VO.ResponseUtil;
import com.imooc.order.client.ProductClient;
import com.imooc.order.converter.OrderForm2OrderDTOConverter;
import com.imooc.order.enums.ResponseEnum;
import com.imooc.order.exception.OrderException;
import com.imooc.order.form.OrderForm;
import com.imooc.order.model.ProductInfo;
import com.imooc.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private ProductClient productClient;

    /**
     * 1 参数校验
     * 2 查询商品信息（调用商品服务）
     * 3 计算总价
     * 4 扣库存（调用商品服务）
     * 5 订单入库
     * @return
     */
    @PostMapping("/create")
    public Response<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new OrderException(ResponseEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResponseEnum.CART_EMPTY);
        }
        OrderDTO result =  orderService.create(orderDTO);
        Map<String,String> data = new HashMap<>();
        data.put("orderId",result.getOrderId());
        return ResponseUtil.success(data);
    }

}
