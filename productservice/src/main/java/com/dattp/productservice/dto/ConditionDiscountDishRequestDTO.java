package com.dattp.productservice.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ConditionDiscountDishRequestDTO {
    private long id;

    @NotNull(message = "Loại điều kiện(type) không được để trống")
    private String type;

    @Min(value = 1, message = "Giá trị của điều kiện(value) không được nhỏ hơn 0")
    private float value;
    
    private String description;
}