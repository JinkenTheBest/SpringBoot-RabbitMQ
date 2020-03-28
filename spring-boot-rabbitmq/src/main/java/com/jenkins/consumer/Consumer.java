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
//    @RabbitListener(queues = AppConfig.QUEUE_NAME)
    public void getLover(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        //这里可以三个参数代表：
        //prefetchSize: 单条消息的大小限制，消费端通常设置为0，表示不做限制
        //prefetchCount: 一次最多能处理多少条消息，通常设置为1
        //global: 是否将上面设置应用于channel，false代表consumer级别
        channel.basicQos(0,1,true);
        try {

            System.out.println(msg + "顾客1确认");
            //这里代表顾客收到了一个面包，false等于是他要一个个对比是不是自己要的，ture的话他就直接全部打包了
            channel.basicAck(tag, false);

            Thread.sleep(10);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @RabbitListener(queues = AppConfig.QUEUE_NAME)
    public void getLover2(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        channel.basicQos(0,1,true);
        try {

            //这里代表顾客收到了一个面包，false等于是他要一个个对比是不是自己要的，ture的话他就直接全部打包了
            System.out.println(msg + "顾客2确认");
            channel.basicAck(tag, false);

            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
