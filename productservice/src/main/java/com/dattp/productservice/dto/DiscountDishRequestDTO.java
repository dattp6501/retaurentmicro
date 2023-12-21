package com.dattp.productservice.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDishRequestDTO {
    @NotEmpty(message = "Mã giảm giá(code) không được để trống")
    private String code;
    
    @Min(value = 1, message = "Mã món ăn(dishId) không được để trống")
    @NotNull(message = "Mã món ăn(dishId) không được để trống")
    private Long dishId;

    @NotEmpty(message = "Loại giảm giá(type) không được để trống")
    private String type;

    @Min(value = 1, message = "Giá trị giảm giá(value) không được để trống")
    private float value;

    @Valid
    private List<ConditionDiscountDishRequestDTO> conditions;

    @NotNull(message = "Thời gian bắt đầu(from) không được để trống")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date from;

    @NotNull(message = "Thời gian kết thúc(to) không được để trống")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;
}
