package com.dattp.productservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.dattp.productservice.command.command.CreateDishCommand;
import com.dattp.productservice.command.event.CreateDishEvent;

import lombok.Getter;

@Aggregate
@Getter
public class DishAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private float price;
    private String discription;
    public DishAggregate(){}
    @CommandHandler
    public DishAggregate(CreateDishCommand createDishCommand){
        CreateDishEvent createDishEvent = new CreateDishEvent();
        BeanUtils.copyProperties(createDishCommand, createDishEvent);
        AggregateLifecycle.apply(createDishEvent);
    }
    @EventSourcingHandler
    public void on(CreateDishEvent createDishEvent){
        this.id = createDishEvent.getId();
        this.name = createDishEvent.getName();
        this.price = createDishEvent.getPrice();
        this.discription = createDishEvent.getDiscription();
    }
    // create
}
