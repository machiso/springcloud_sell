package com.imooc.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqReceiver {
    @RabbitListener(queuesToDeclare=@Queue("myQueue"))
    public void proecess(String message){
        log.info("MqReceiver:{}",message);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange =@Exchange("myExchange"),
            value = @Queue("myQueue")
    ))
    public void proceess(String message){
        log.info("MqReceiver:{}",message);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange =@Exchange("myExchange"),
            key = "computer",
            value = @Queue("myQueue")
    ))
    public void computerQueue(String message){
        log.info("MqReceiver:{}",message);
    }
}
