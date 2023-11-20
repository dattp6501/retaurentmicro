package com.dattp.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dattp.authservice.entity.User;



public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
}