package com.dattp.productservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDishDTO {
    private int state;

    private long id;

    private String name;

    private float price;

    private String discription;

    public ResponseDishDTO(int state, long id, String name, float price, String discription) {
        this.state = state;
        this.id = id;
        this.name = name;
        this.price = price;
        this.discription = discription;
    }

    public ResponseDishDTO() {
    }
}