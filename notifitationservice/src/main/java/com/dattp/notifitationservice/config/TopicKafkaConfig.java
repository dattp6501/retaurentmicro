package com.dattp.notifitationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicKafkaConfig {
    @Bean
    public NewTopic newUserTopic(){
        return TopicBuilder.name("newUser").build();
    }

    @Bean
    public NewTopic notificationOrderTopic(){
        return TopicBuilder.name("notiOrder").build();
    }

    @Bean
    public NewTopic paymentOrderTopic(){
        return TopicBuilder.name("paymentsOrder").build();
    }
}