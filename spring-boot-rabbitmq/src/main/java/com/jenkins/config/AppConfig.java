package com.jenkins.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig implements RabbitTemplate.ReturnCallback ,RabbitTemplate.ConfirmCallback {

    @Autowired
    RabbitTemplate template;
    public final static String QUEUE_NAME = "helloWorldQueue";
    public final static String EXCHANGE_NAME = "helloWorldExchang";
    public final static String ROUTING_KEY = "helloWorldKey";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding(Queue queue , TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public TopicExchange topiceExchang(){

        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);

        return rabbitTemplate;
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("没有能够进入展示柜");
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b){
            System.out.println("面包到达了窗口（可惜不是我家的，所以可以扣工资了）");
        }else {
            System.out.println("面包没有到达窗口");

        }
    }

    @PostConstruct
    public void init(){
        template.setConfirmCallback(this);
        template.setReturnCallback(this);
    }
}
