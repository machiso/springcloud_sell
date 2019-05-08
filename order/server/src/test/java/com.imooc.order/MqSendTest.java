package com.imooc.order;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MqSendTest extends OrderApplicationTests{

    @Autowired private AmqpTemplate amqpTemplate;

    @Test
    public void mqTest(){
        amqpTemplate.convertAndSend("myQueue","now:"+new Date());
    }

    @Test
    public void mqComputer(){
        amqpTemplate.convertAndSend("myExchange","computer","now:"+new Date());
    }

}
