package com.dattp.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedDishResponseDTO {
    private int state;
    private long id;
    private long dishId;
    private int total;
    private float price;
    public BookedDishResponseDTO(long id, long dishId, int total, float price) {
        this.id = id;
        this.dishId = dishId;
        this.total = total;
        this.price = price;
    }
    public BookedDishResponseDTO() {
    }
    @Override
    public boolean equals(Object obj) {
        BookedDishResponseDTO other = (BookedDishResponseDTO) obj;
        return id == other.id;
    }
}