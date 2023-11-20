package com.dattp.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.authservice.entity.Role;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    
    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public Role getByID(Long id){
        return roleRepository.findById(id).orElse(null);
    }
    public List<Role> getRoles(User user){
        return roleRepository.getRoles(user.getUsername());
    }
}
