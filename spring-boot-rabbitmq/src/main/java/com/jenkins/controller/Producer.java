package com.jenkins.controller;

import com.jenkins.config.AppConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Producer {
    @Autowired
    private RabbitTemplate template;

    @GetMapping("/makeLover")
    public String makeLover() {
        for (int i = 0; i < 50; i++) {
            String msg = "send msg = " + i;
            try {
                //每发一条消息睡一会会
                Thread.sleep(i * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            template.convertAndSend(AppConfig.EXCHANGE_NAME, AppConfig.ROUTING_KEY, msg);
        }
        return "OK";
    }
}
