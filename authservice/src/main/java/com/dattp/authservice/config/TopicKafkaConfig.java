package com.dattp.authservice.config;

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
    public NewTopic verifiUserTopic(){
        return TopicBuilder.name("verifiUser").build();
    }
}
