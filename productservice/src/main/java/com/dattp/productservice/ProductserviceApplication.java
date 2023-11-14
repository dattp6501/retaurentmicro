package com.dattp.productservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductserviceApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}
	// http://localhost:9001/h2-console
	@Override
	public void run(String... args) throws Exception {
	}
}