package com.dattp.notifitationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicKafkaConfig {
    @Bean
    public NewTopic newUserTopic(){
        return TopicBuilder.name("new-user").build();
    }

    @Bean
    public NewTopic newNotificationOrderTopic(){
        return TopicBuilder.name("noti-order").build();
    }
}