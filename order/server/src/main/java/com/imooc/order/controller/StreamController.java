package com.imooc.order.controller;

import com.imooc.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class StreamController {
    @Autowired private StreamClient streamClient;

    @GetMapping("/send")
    public void send(){
        streamClient.output().send(MessageBuilder.withPayload("now"+new Date()).build());
    }
}
