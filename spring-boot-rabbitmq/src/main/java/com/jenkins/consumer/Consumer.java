package com.jenkins.consumer;

import com.jenkins.config.AppConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

public class Consumer {
    // tag代表投递的标识符，唯一标识了当前信道上的投递，通过 deliveryTag ，消费者就可以告诉 RabbitMQ 确认收到了当前消息
    @RabbitListener(queues = AppConfig.QUEUE_NAME)
    @SendTo(AppConfig.QUEUE_NAME)
    public String getLover(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)Long tag){
            try {
                if (msg.contains("1")) {
                    //这里代表顾客收到了一个面包，false等于是他要一个个对比是不是自己要的，ture的话他就直接全部打包了
                    channel.basicAck(tag, false);
                    System.out.println(msg+"顾客确认");
                    return "已支付28000元";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
       return null;
    }
}
