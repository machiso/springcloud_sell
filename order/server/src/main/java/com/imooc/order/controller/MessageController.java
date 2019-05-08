package com.imooc.order.controller;

import com.imooc.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MessageController {
    @Autowired private StreamClient streamClient;

    @RequestMapping("/sendMessage")
    public void sendMessage(){
        String message = "time="+new Date();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
    }
}
