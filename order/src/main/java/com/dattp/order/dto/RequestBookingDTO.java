package com.dattp.order.dto;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter
public class RequestBookingDTO {

    //customer id
    @NotNull(message = "ID khách hàng(customer_id) không được để trống")
    @JsonProperty("customer_id")
    private long customerID;
    //create date

    @NotNull(message = "Phải có ít nhất 1 bàn được đặt")
    @Size(min = 1, message = "Phải có ít nhất 1 bàn được đặt")
    @Valid// kiểm tra các phần tử bên trong danh sách
    // @JsonProperty("tables")
    private Collection<RequestBookedTableDTO> tables;

    private String description;

}