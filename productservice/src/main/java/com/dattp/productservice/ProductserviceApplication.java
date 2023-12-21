package com.dattp.productservice;

import java.util.List;

import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import com.dattp.productservice.service.RedisService;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductserviceApplication{
	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}
	@Bean
	CommandLineRunner run(RedisService redisService){
		return arg0->{
			
		};
	}
}