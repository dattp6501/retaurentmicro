package com.dattp.productservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic productTopic(){
        return TopicBuilder.name("productTopic").build();
    }
    // @Bean
    // public NewTopic createBookingTopic(){
    //     return TopicBuilder.name("createBookingTopic").build();
    // }
}