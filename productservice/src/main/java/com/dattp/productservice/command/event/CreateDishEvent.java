package com.dattp.productservice.command.event;

import lombok.Getter;

@Getter
public class CreateDishEvent {
    private String id;
    private String name;
    private float price;
    private String discription;
    public CreateDishEvent() {
    }
}
