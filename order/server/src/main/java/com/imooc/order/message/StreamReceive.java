package com.imooc.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@EnableBinding(StreamClient.class)
@Component
@Slf4j
public class StreamReceive {

    @StreamListener(StreamClient.OUTPUT)
    public void processOutput(Object message){
        log.info("StreamReceive:{}",message);
    }
}
