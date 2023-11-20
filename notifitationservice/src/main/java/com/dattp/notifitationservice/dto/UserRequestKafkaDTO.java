package com.dattp.notifitationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestKafkaDTO {
    private Long id;
    private String fullname;
    private String username;
    private String mail;
    public UserRequestKafkaDTO(Long id, String fullname, String username, String mail) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.mail = mail;
    }
    public UserRequestKafkaDTO() {
    }
}
