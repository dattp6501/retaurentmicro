package com.dattp.authservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date createdAt;
    private String fullname;
    private String username;
    private String mail;

    public UserResponseDTO() {
    }
}
