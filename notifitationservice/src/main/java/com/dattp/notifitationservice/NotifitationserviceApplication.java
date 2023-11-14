package com.dattp.notifitationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NotifitationserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(NotifitationserviceApplication.class, args);
	}

}
