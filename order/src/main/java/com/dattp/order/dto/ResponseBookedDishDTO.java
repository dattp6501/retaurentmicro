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
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ResponseBookedDishDTO))
            return false;
        ResponseBookedDishDTO other = (ResponseBookedDishDTO) obj;
        if (id != other.id)
            return false;
        return true;
    }
}