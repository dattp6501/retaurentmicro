package com.dattp.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication implements CommandLineRunner{

	public static void main(String[] args){
		SpringApplication.run(OrderApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// List<BookedTable> tables = new ArrayList<>();
		// tables.add(new BookedTable(1,100000,new Date(), new Date(), null, null));
		// Booking booking = new Booking(-1, 1, new Date(), null, tables);
		// System.out.println(bookingService.save(booking));

		// bookedTableService.getBookedTable(new Date(), new Date());
	}
}