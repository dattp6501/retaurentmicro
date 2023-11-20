package com.dattp.productservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableRequestDTO {
    
    private long id;

    @NotNull(message = "Thiếu trường dữ liệu name khi gửi đi") @NotEmpty(message = "Tên bàn không được để trống")
    private String name;

    @Min(value = 1, message = "Số người có thể chứa phải lớn hơn hoặc bằng 0")
    private int amountOfPeople;

    @Min(value = 1, message = "Giá thuê phải lớn hơn hoặc bằng 0")
    private float price;

    private String desciption;
}