package com.dattp.paymentservice.dto.kafka;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingRequestKafkaDTO {
    private long id;
    private long CustomerId;
    private float deposits;

    private String email;
    private String custemerFullname;
    
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date date;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date from;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;
    private String description;
    public BookingRequestKafkaDTO() {
    }
}