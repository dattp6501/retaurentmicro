package com.dattp.productservice.dto.kafka;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedTableRequestKafkaDTO {
    private int state;
    private long id;
    private long tableId;
    private String name;
    private float price;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date from;
    
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;
    private Collection<BookedDishRequestKafkaDTO> dishs;
    public BookedTableRequestKafkaDTO(long id, long tableId, String name, float price, Date from, Date to, Collection<BookedDishRequestKafkaDTO> dishs) {
        this.id = id;
        this.tableId = tableId;
        this.name = name;
        this.price = price;
        this.from = from;
        this.to = to;
        this.dishs = dishs;
    }
    public BookedTableRequestKafkaDTO() {
    }
}