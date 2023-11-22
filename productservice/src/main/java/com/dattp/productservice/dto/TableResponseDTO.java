package com.dattp.productservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableResponseDTO{
    private int state;

    private long id;

    private String name;

    private int amountOfPeople;

    private float price;

    @JsonFormat(pattern = "HH:mm")
    private Date from;

    @JsonFormat(pattern = "HH:mm")
    private Date to;

    private String desciption;

    public TableResponseDTO(int state, long id, String name, int amountOfPeople, float price, Date from, Date to, String desciption) {
        this.state = state;
        this.id = id;
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.price = price;
        this.from = from;
        this.to = to;
        this.desciption = desciption;
    }

    public TableResponseDTO() {
    }
}