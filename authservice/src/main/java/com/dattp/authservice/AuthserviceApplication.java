package com.dattp.authservice;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dattp.authservice.entity.Role;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.service.RoleService;
import com.dattp.authservice.service.UserService;


@SpringBootApplication
public class AuthserviceApplication{

	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleService roleService, UserService userService){
		return arg0->{
			Role roleUser = roleService.saveRole(new Role(null, "ROLE_USER", true));
			System.out.println(roleUser);
			Role roleManager = roleService.saveRole(new Role(null, "ROLE_MANAGER", true));
			Role roleAdmin = roleService.saveRole(new Role(null, "ROLE_ADMIN", true));
			Role roleSuperAdmin = roleService.saveRole(new Role(null, "ROLE_SUPERADMIN", true));


			User user1 = userService.saveUser(new User(null, "Nguyen Van A", "anv", "123456789","anv@gmail.com", new ArrayList<>()));
			User user2 = userService.saveUser(new User(null, "Truong Phuc Dat", "dattp", "dattp","dattp@gmail.com", new ArrayList<>()));

			userService.addRoleToUser(user1.getId(), roleUser.getId());
			userService.addRoleToUser(user1.getId(), roleManager.getId());
			userService.addRoleToUser(user2.getId(), roleAdmin.getId());
			userService.addRoleToUser(user2.getId(), roleSuperAdmin.getId());
		};
	}
}
// https://www.youtube.com/watch?v=oGsQM6qjYQ8 56:43 