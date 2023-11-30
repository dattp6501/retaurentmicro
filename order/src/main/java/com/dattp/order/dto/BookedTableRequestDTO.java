package com.dattp.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter
public class BookedTableRequestDTO {
    // private int state;
    // private long id;
    @Min(value = 1, message = "Bàn(table_id) chưa được chọn or không tồn tại")  
    @JsonProperty("tableId")  
    private long tableId;

    @NotNull(message = "Tên bàn(name) không được để trống")
    private String name;

    // @Min(value = 1, message = "Giá bàn(price) phải lớn hơn 0")
    // private float price;

    // @Valid
    // @JsonProperty("dishs")
    // private List<BookedDishRequestDTO> dishs;
}
