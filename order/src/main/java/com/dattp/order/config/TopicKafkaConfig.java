package com.dattp.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicKafkaConfig {
    @Bean
    public NewTopic createBookingTopic(){
        return TopicBuilder.name("newOrder").build();
    }

    @Bean
    public NewTopic resultCheckBookingTopic(){
        return TopicBuilder.name("checkOrder").build();
    }

    @Bean
    public NewTopic notificationOrderTopic(){
        return TopicBuilder.name("notiOrder").build();
    }
}