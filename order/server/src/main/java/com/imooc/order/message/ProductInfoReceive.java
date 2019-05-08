package com.imooc.order.message;

import com.alibaba.fastjson.JSONObject;
import common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductInfoReceive {

    private static final String PRODUCT_STOCK_TEMPLATE="product_stock_%s";

    @Autowired private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        ProductInfoOutput output = JSONObject.parseObject(message, ProductInfoOutput.class);
        log.info("从队列【{}】接收消息{}","productInfo",output);

        stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,output.getProductId()),
                String.valueOf(output.getProductStock()));
    }
}
