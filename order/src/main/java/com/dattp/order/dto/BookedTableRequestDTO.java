package com.dattp.order.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter
public class BookedTableRequestDTO {
    private int state;
    private long id;
    @Min(value = 1, message = "Bàn(table_id) chưa được chọn or không tồn tại")  
    @JsonProperty("table_id")  
    private long tableId;

    @Min(value = 1, message = "Giá bàn(price) phải lớn hơn 0")
    private float price;

    @NotNull(message = "Thời gian bắt đầu(from) không được bỏ trống")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date from;

    @NotNull(message = "Thời gian kết thúc(to) không được bỏ trống")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date to;

    @Valid
    // @JsonProperty("dishs")
    private List<BookedDishRequestDTO> dishs;
}
