package com.dattp.order.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ResponseDTO {
    private int code;
    private String message;
    public ResponseDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }
}