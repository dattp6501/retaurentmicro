package com.dattp.productservice.dto;

import com.dattp.productservice.entity.TableE;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTableDTO{
    private int code;
    private String message;
    private TableE table;
    @Builder
    public ResponseTableDTO(int code, String message, TableE table) {
        this.code = code;
        this.message = message;
        this.table = table;
    }
}