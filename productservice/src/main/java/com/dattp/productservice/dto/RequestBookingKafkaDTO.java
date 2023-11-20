package com.dattp.productservice.dto;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestBookingKafkaDTO {
    private int state;
    private long id;
    private long CustomerId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String description;
    private Collection<RequestBookedTableKafkaDTO> bookedTables;
    public RequestBookingKafkaDTO(long id, long customerId, Date date, String description, Collection<RequestBookedTableKafkaDTO> bookedTables) {
        this.id = id;
        CustomerId = customerId;
        this.date = date;
        this.description = description;
        this.bookedTables = bookedTables;
    }
    public RequestBookingKafkaDTO() {
    }
}