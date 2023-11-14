package com.dattp.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dattp.authservice.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    public User findByUsername(String username);
}