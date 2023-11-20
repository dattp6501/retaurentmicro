package com.dattp.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic newUserTopic(){
        return TopicBuilder.name("new-user").build();
    }

    @Bean
    public NewTopic verifiUserTopic(){
        return TopicBuilder.name("verifi-user").build();
    }
}
