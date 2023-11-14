package com.dattp.productservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO{
    private int code;
    private String message;
    @Builder
    public ResponseDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }
}