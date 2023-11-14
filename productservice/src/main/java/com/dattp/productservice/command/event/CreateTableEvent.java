package com.dattp.productservice.command.event;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTableEvent {
    private String id;
    private String name;
    private int amountOfPeople;
    private float price;
    private String desciption;
    public CreateTableEvent() {
    }
}
