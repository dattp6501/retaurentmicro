package com.dattp.productservice.command.event;

import java.util.List;

import com.dattp.productservice.entity.Dish;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateListDishEvent {
    private String id;
    private List<Dish> dishs;
    public CreateListDishEvent(){}
}
