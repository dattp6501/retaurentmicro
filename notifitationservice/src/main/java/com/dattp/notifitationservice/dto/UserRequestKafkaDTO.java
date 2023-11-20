package com.dattp.notifitationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestKafkaDTO {
    private Long id;
    private String fullname;
    private String username;
    private String password;
    private String mail;
    public UserRequestKafkaDTO(Long id, String fullname, String username, String password, String mail) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.mail = mail;
    }
    public UserRequestKafkaDTO() {
    }
}
