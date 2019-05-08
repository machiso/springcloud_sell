package com.imooc.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@EnableBinding(StreamClient.class)
@Component
@Slf4j
public class StreamRecv {
    @StreamListener(StreamClient.OUTPUT)
    public void process(Object o){
      log.info("【StreamRecv:】{}",o);
    }

}
