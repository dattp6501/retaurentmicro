package com.dattp.productservice.command.event;

import java.io.IOException;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dattp.productservice.entity.Dish;
import com.dattp.productservice.service.DishService;

@Component
public class DishEventHandler {
    @Autowired
    private DishService dishService;

    @EventHandler
    public void on(CreateDishEvent createDishEvent){
        Dish dish = new Dish();
        BeanUtils.copyProperties(createDishEvent, dish);
        dishService.save(dish);
    }

    @EventHandler
    public void on(CreateListDishEvent createListDishEvent) throws IOException{
        dishService.save(createListDishEvent.getDishs());
    }
}