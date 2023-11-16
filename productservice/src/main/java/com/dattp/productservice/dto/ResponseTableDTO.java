package com.dattp.productservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTableDTO{
    private int state;

    private long id;

    private String name;

    private int amountOfPeople;

    private float price;

    private String desciption;

    public ResponseTableDTO(int state, long id, String name, int amountOfPeople, float price, String desciption) {
        this.state = state;
        this.id = id;
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.price = price;
        this.desciption = desciption;
    }

    public ResponseTableDTO() {
    }
}