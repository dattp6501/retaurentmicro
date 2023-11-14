package com.dattp.productservice.command.aggregate;

import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.dattp.productservice.command.command.CreateListDishCommand;
import com.dattp.productservice.command.event.CreateListDishEvent;
import com.dattp.productservice.entity.Dish;

import lombok.Getter;

@Aggregate
@Getter
public class ListDishAggregate {
    @AggregateIdentifier
    private String id;
    private List<Dish> dishs;
    public ListDishAggregate(){}
    // create dish with file
    @CommandHandler
    public ListDishAggregate(CreateListDishCommand command){
        CreateListDishEvent createListDishEvent = new CreateListDishEvent();
        createListDishEvent.setId(command.getId());
        createListDishEvent.setDishs(command.getDishs());
        AggregateLifecycle.apply(createListDishEvent);
    }
    @EventSourcingHandler
    public void on(CreateListDishEvent createListDishEvent){
        this.id = createListDishEvent.getId();
        this.dishs = createListDishEvent.getDishs();
    }
}
