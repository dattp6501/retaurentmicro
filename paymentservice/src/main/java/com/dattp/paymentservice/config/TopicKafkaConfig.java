package com.dattp.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicKafkaConfig {
    @Bean
    public NewTopic createPaymentOrderTopic(){
        return TopicBuilder.name("createPaymentOrder").build();
    }

    @Bean
    public NewTopic paymentOrderSuccessTopic(){
        return TopicBuilder.name("paymentOrderSuccess").build();
    }
}