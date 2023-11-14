package com.dattp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner{
// https://www.youtube.com/watch?v=SqVfCyfCJqw
	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}
	@Autowired
	KafkaTemplate<String,String> kafkaTemplate;
	@Override
	public void run(String... args) throws Exception {
		System.out.println("xin chao");
		kafkaTemplate.send("product", "Xin chao");
	}

}
