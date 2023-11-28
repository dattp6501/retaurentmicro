package com.dattp.authservice.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    @NotNull(message = "Tên đăng nhập(username) không được để trống")
    private String username;

    @NotNull(message = "Mật khẩu(password) không được để trống")
    private String password;
    public AuthRequestDTO() {
    }
    public AuthRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}