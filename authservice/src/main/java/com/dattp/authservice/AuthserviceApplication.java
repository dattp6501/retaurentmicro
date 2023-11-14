package com.dattp.authservice;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dattp.authservice.entity.User;
import com.dattp.authservice.repository.UserRepository;


@SpringBootApplication
public class AuthserviceApplication{

	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

	@Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

	// @Override
	// public void run(String... args) throws Exception {
	// 	// Khi chương trình chạy
    //     // Insert vào csdl một user.
    //     // User user = new User();
    //     // user.setUsername("loda");
    //     // user.setPassword(passwordEncoder.encode("loda"));
    //     // userRepository.save(user);
    //     // System.out.println(user);
	// }
}