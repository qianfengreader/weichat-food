package com.qf.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by 54110 on 2020/12/31.
 */
@Component
public class RabbitMqConfig {

    @Bean
    public Queue queue(){
        return new Queue("send-mail");
    }

    @Bean
    public Queue queue1(){
        return new Queue("transfer-queue");
    }

    @Bean
    public Queue queue2(){
        return new Queue("transfer-queuetwo");
    }
}
