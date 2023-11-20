package com.dattp.productservice.dto;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBookedTableKafkaDTO {
    private int state;
    private long id;
    private long tableId;
    private float price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date from;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date to;
    private Collection<RequestBookedDishKafkaDTO> dishs;
    public RequestBookedTableKafkaDTO(long id, long tableId, float price, Date from, Date to, Collection<RequestBookedDishKafkaDTO> dishs) {
        this.id = id;
        this.tableId = tableId;
        this.price = price;
        this.from = from;
        this.to = to;
        this.dishs = dishs;
    }
    public RequestBookedTableKafkaDTO() {
    }
}