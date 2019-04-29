package com.imooc.order.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.order.DTO.OrderDTO;
import com.imooc.order.enums.ResponseEnum;
import com.imooc.order.exception.OrderException;
import com.imooc.order.form.OrderForm;
import com.imooc.order.model.OrderDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //jsonobject  json字符串 -> list数组
        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
            orderDetails = JSONObject.parseArray(orderForm.getItems(), OrderDetail.class);
        }catch (Exception e){
            log.error("【json转换】出错,json={}",orderForm.getItems());
            throw new OrderException(ResponseEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetails);

        return orderDTO;
    }

    public static void main(String[] args) {
        String msg = "[{\n" +
                "    productId: \"1423113435324\",\n" +
                "    productQuantity: 2 //购买数量\n" +
                "},{\n" +
                "    productId: \"4353647457577\",\n" +
                "    productQuantity: 3 //购买数量\n" +
                "}]";

        List<OrderDetail> orderDetails = JSONArray.parseArray(msg, OrderDetail.class);
        for(OrderDetail orderDetail : orderDetails){
            System.out.println(orderDetail.getProductId());
        }
    }
}
