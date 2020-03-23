package com.jenkins.controller;

import com.jenkins.config.AppConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Producer {
    @Autowired
    RabbitTemplate template;

    @GetMapping("/makeLover")
    public String makeLover(){
        for (int i = 0; i < 5; i++) {
             String msg = "send msg = " + i;
            template.convertAndSend(AppConfig.EXCHANGE_NAME,AppConfig.ROUTING_KEY,msg);
        }
        return "OK";
    }


}
