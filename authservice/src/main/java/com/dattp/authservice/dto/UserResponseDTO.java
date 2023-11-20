package com.dattp.authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String fullname;
    private String username;
    private String mail;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String fullname, String username, String mail) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.mail = mail;
    }
}
