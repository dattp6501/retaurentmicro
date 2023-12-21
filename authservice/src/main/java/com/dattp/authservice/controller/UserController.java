package com.dattp.authservice.controller;

import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.authservice.dto.AuthRequestDTO;
import com.dattp.authservice.dto.ResponseDTO;
import com.dattp.authservice.dto.UserRequestDTO;
import com.dattp.authservice.dto.UserResponseDTO;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.service.AuthenticationService;
import com.dattp.authservice.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String,UserResponseDTO> kafkaTemplateUser;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid AuthRequestDTO authenticationRequest){
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", authenticationService.authenticate(authenticationRequest))
        );
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid UserRequestDTO userR){
        User newUser = new User();
        BeanUtils.copyProperties(userR, newUser);
        newUser.setCreatedAt(new Date());
        // luu vao CSDL
        newUser = userService.saveUser(newUser);
        UserResponseDTO userResp = new UserResponseDTO();
        BeanUtils.copyProperties(newUser, userResp);
        // gui thong diep den notification service de gui thong bao xac thuc
        try {
            kafkaTemplateUser.send("newUser", userResp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", userResp)
        );
    }

    @GetMapping
    @RolesAllowed({"ROLE_PRODUCT_ACCESS"})
    @RequestMapping("/profile")
    public ResponseEntity<ResponseDTO> profile(){
        Long userId = Long.parseLong(
            SecurityContextHolder.getContext().getAuthentication().getName().split("///")[0]
        );
        User user = userService.getByID(userId);
        UserResponseDTO userResp = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResp);
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", userResp)
        );
    }

    @GetMapping
    @RequestMapping("/context")
    public SecurityContext context(){
        return SecurityContextHolder.getContext();
    }
}