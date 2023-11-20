package com.dattp.order.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter
public class BookingRequestDTO {
    private int state;
    private long id;
    //customer id
    @NotNull(message = "ID khách hàng(customer_id) không được để trống")
    @JsonProperty("customer_id")
    private Long customerId;
    //create date

    @NotNull(message = "Phải có ít nhất 1 bàn được đặt")
    @Size(min = 1, message = "Phải có ít nhất 1 bàn được đặt")
    @Valid// kiểm tra các phần tử bên trong danh sách
    @JsonProperty("tables")
    private List<BookedTableRequestDTO> bookedTables;

    private String description;

}