package com.dattp.authservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dattp.authservice.entity.Role;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.repository.RoleRepository;
import com.dattp.authservice.repository.UserRepository;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getByID(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public void addRoleToUser(String username, String rolename){
        User user = userRepository.findByUsername(username).orElseThrow();
        Role role = roleRepository.findByName(rolename).orElseThrow();
        user.getRoles().add(role);
    }
}
