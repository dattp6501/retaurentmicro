package com.dattp.productservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedDishRequestKafkaDTO {
    private int state;
    private long id;
    private long dishId;
    private int total;
    private float price;
    public BookedDishRequestKafkaDTO(long id, long dishId, int total, float price) {
        this.id = id;
        this.dishId = dishId;
        this.total = total;
        this.price = price;
    }
    public BookedDishRequestKafkaDTO() {
    }
}
