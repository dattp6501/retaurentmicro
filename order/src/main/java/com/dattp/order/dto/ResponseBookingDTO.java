package com.dattp.order.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBookingDTO {
    private int state;
    private long id;
    private long CustomerId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String description;
    private List<ResponseBookedTableDTO> bookedTables;
    public ResponseBookingDTO(){}
}