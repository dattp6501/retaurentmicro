package com.dattp.productservice.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

@Getter
public class CreateDishCommand {
    @TargetAggregateIdentifier
    private String id;
    private String name;
    private float price;
    private String discription;
    public CreateDishCommand(String id, String name, float price, String discription) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discription = discription;
    }
}
