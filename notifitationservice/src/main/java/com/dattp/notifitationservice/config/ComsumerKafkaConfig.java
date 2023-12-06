package com.dattp.notifitationservice.config;

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

import com.dattp.notifitationservice.dto.kafka.BookingRequestKafkaDTO;
import com.dattp.notifitationservice.dto.kafka.UserRequestKafkaDTO;

@Configuration
@EnableKafka
public class ComsumerKafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;
    // 
    public Map<String, Object> comsumerConfigJSON(){
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }
    // booking
    @Bean
    public ConsumerFactory<String,BookingRequestKafkaDTO> consumerFactoryBooking(){
        JsonDeserializer<BookingRequestKafkaDTO> jsonDeserializernew = new JsonDeserializer<>(BookingRequestKafkaDTO.class, false);
        jsonDeserializernew.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(comsumerConfigJSON(),new StringDeserializer(), jsonDeserializernew);
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,BookingRequestKafkaDTO>> factoryBooking(ConsumerFactory<String,BookingRequestKafkaDTO> consumerFactoryBooking){
        ConcurrentKafkaListenerContainerFactory<String,BookingRequestKafkaDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryBooking);
        return factory;
    } 
    // user
    @Bean
    public ConsumerFactory<String,UserRequestKafkaDTO> consumerFactoryUser(){
        JsonDeserializer<UserRequestKafkaDTO> jsonDeserializernew = new JsonDeserializer<>(UserRequestKafkaDTO.class, false);
        jsonDeserializernew.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(comsumerConfigJSON(),new StringDeserializer(), jsonDeserializernew);
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,UserRequestKafkaDTO>> factoryUser(ConsumerFactory<String,UserRequestKafkaDTO> consumerFactoryUser){
        ConcurrentKafkaListenerContainerFactory<String,UserRequestKafkaDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryUser);
        return factory;
    } 
}
