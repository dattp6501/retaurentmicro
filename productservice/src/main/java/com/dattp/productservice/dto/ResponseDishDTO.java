package com.dattp.productservice.dto;

import com.dattp.productservice.entity.Dish;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDishDTO {
    private int code;
    private String message;
    private Dish dish;
    @Builder
    public ResponseDishDTO(int code, String message, Dish dish) {
        this.code = code;
        this.message = message;
        this.dish = dish;
    }
}