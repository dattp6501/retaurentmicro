package com.dattp.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishInCartRequestDTO {
    @JsonProperty("dishId")
    @NotNull(message="dishId không được để trống")
    private Long dishId;

    @NotNull(message="Tên món(name) không được để trống")
    private String name;

    @Min(value = 1, message = "GIá món phải > 0")
    private float price;

    @Min(value = 1, message = "Số lượng đặt(total) phải > 0")
    private int total;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dishId == null) ? 0 : dishId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof DishInCartRequestDTO))
            return false;
        DishInCartRequestDTO other = (DishInCartRequestDTO) obj;
        if (dishId == null) {
            if (other.dishId != null)
                return false;
        } else if (!dishId.equals(other.dishId))
            return false;
        return true;
    }
}
