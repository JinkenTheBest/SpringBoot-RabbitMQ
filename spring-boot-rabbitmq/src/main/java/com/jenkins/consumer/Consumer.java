package com.jenkins.consumer;

import com.jenkins.config.AppConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @RabbitListener(queues = AppConfig.QUEUE_NAME)
    public void getLover(String msg){
        System.out.println(msg);
    }
}
