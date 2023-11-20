package com.dattp.authservice.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.authservice.dto.ResponseDTO;
import com.dattp.authservice.dto.UserRequestDTO;
import com.dattp.authservice.dto.UserResponseDTO;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String,UserResponseDTO> kafkaTemplateUser;

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid UserRequestDTO userR){
        User newUser = new User();
        BeanUtils.copyProperties(userR, newUser);
        // luu vao CSDL
        newUser = userService.saveUser(newUser);
        UserResponseDTO userResp = new UserResponseDTO();
        BeanUtils.copyProperties(newUser, userResp);
        // gui thong diep den notification service
        kafkaTemplateUser.send("new-user", userResp);
        
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", userResp)
        );
    }
}
