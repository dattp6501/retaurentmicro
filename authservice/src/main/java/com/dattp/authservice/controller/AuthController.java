package com.dattp.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.authservice.dto.AuthRequestDTO;
import com.dattp.authservice.dto.AuthResponseDTO;
import com.dattp.authservice.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authenticationRequest){
        return ResponseEntity.ok().body(
            authenticationService.authenticate(authenticationRequest)
        );
    }

    @GetMapping
    @RequestMapping("/home_user")
    public ResponseEntity<String> homeUser(){
        return ResponseEntity.ok().body(
            "HOME USER"
        );
    }
    
}