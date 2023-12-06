package com.dattp.notifitationservice.dto.kafka;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingRequestKafkaDTO {
    private int state;
    private long id;
    private long CustomerId;
    private float deposits;

    private String custemerFullname;
    
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date date;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date from;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;
    private String description;
    private Collection<BookedTableRequestKafkaDTO> bookedTables;
    public BookingRequestKafkaDTO(long id, long customerId, Date date, String description, Collection<BookedTableRequestKafkaDTO> bookedTables) {
        this.id = id;
        CustomerId = customerId;
        this.date = date;
        this.description = description;
        this.bookedTables = bookedTables;
    }
    public BookingRequestKafkaDTO() {
    }
}