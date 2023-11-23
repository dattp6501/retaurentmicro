package com.dattp.productservice.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDTO {
    private int state;

    private long id;

    private String name;

    private float price;

    private List<CommentDishResponseDTO> comments;

    private String discription;

    public DishResponseDTO(int state, long id, String name, float price, String discription) {
        this.state = state;
        this.id = id;
        this.name = name;
        this.price = price;
        this.discription = discription;
    }

    public DishResponseDTO() {
    }
}