package com.dattp.order;


import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import com.dattp.order.entity.Cart;
import com.dattp.order.service.CartService;
import com.dattp.order.service.RedisService;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication{

	public static void main(String[] args){
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CartService cartService, RedisTemplate<Object, Object> template, RedisService redisService){
		return arg0->{
			// 
			cartService.createCart(new Cart(Long.parseLong("1")));
			if(!template.hasKey("statictisOrder")){
				JSONObject statictisOrder = new JSONObject();
				statictisOrder.put("total", 0);
				statictisOrder.put("confirm", 0);
				statictisOrder.put("cancel", 0);
				statictisOrder.put("wait", 0);
				template.opsForValue().set("statictisOrder", statictisOrder.toString());
			}
		};
	}
}