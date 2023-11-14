package com.dattp.productservice.command.command;

import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.dattp.productservice.entity.Dish;

import lombok.Getter;

@Getter
public class CreateListDishCommand {
    @TargetAggregateIdentifier
    private String id;
    private List<Dish> dishs;
    public CreateListDishCommand(String id, List<Dish> dishs) {
        this.id = id;
        this.dishs = dishs;
    }
}
