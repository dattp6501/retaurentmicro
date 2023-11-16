package com.dattp.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBookedDishDTO {
    private int state;
    private long id;
    private long dishID;
    private int total;
    private float price;
    public ResponseBookedDishDTO(long id, long dishID, int total, float price) {
        this.id = id;
        this.dishID = dishID;
        this.total = total;
        this.price = price;
    }
    public ResponseBookedDishDTO() {
    }
}