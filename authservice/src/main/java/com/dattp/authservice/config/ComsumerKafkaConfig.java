package com.dattp.authservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.dattp.authservice.dto.UserResponseDTO;

@Configuration
@EnableKafka
public class ComsumerKafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    // 
    public Map<String, Object> comsumerConfigUser(){
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }
    // user
    @Bean
    public ConsumerFactory<String,UserResponseDTO> consumerFactoryUser(){
        JsonDeserializer<UserResponseDTO> jsonDeserializernew = new JsonDeserializer<>(UserResponseDTO.class, false);
        jsonDeserializernew.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(comsumerConfigUser(),new StringDeserializer(), jsonDeserializernew);
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,UserResponseDTO>> factoryUser(ConsumerFactory<String,UserResponseDTO> consumerFactoryUser){
        ConcurrentKafkaListenerContainerFactory<String,UserResponseDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryUser);
        return factory;
    } 
}