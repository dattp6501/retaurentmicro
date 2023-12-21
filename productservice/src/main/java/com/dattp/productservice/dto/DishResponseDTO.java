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

    private float discountAmount;

    private List<CommentDishResponseDTO> comments;

    private DiscountDishResponseDTO discount;

    private String description;
    
    public void setDiscount(DiscountDishResponseDTO discount){
        this.discount =discount;
    }

    public DishResponseDTO() {
    }
}