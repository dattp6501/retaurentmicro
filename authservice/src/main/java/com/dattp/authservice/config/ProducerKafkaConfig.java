package com.dattp.authservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.dattp.authservice.dto.UserResponseDTO;

@Configuration
public class ProducerKafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    // 
    public Map<String, Object> producerConfigJSON(){
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    // user
    @Bean
    public ProducerFactory<String,UserResponseDTO> producerFactoryUser(){
        return new DefaultKafkaProducerFactory<>(producerConfigJSON());
    }
    @Bean
    public KafkaTemplate<String,UserResponseDTO> kafkaTemplateUser(ProducerFactory<String,UserResponseDTO> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
    // 
}
