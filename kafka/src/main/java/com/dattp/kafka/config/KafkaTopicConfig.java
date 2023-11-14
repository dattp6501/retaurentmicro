package com.dattp.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
/**
 * KafkaTopConfig
 */
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic productTopic(){
        return TopicBuilder.name("product").build();
    }
    
}