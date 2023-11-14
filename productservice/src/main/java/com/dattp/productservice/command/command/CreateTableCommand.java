package com.dattp.productservice.command.command;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

@Getter
public class CreateTableCommand {
    @TargetAggregateIdentifier
    private String id;
    private String name;
    private int amountOfPeople;
    private float price;
    private String desciption;
    public CreateTableCommand(String id, String name, int amountOfPeople, float price, String desciption) {
        this.id = id;
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.price = price;
        this.desciption = desciption;
    }
}
