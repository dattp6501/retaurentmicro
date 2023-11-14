package com.dattp.productservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.dattp.productservice.command.command.CreateTableCommand;
import com.dattp.productservice.command.event.CreateTableEvent;

import lombok.Getter;

@Aggregate
@Getter
public class TableAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private int amountOfPeople;
    private float price;
    private String desciption;
    public TableAggregate() {
    }
    @CommandHandler
    public TableAggregate(CreateTableCommand createTableCommand){
        CreateTableEvent CreateTableEvent = new CreateTableEvent();
        BeanUtils.copyProperties(createTableCommand, CreateTableEvent);
        AggregateLifecycle.apply(CreateTableEvent);
    }
    @EventSourcingHandler
    public void on(CreateTableEvent createTableEvent){
        this.id = createTableEvent.getId();
        this.name = createTableEvent.getName();
        this.amountOfPeople = createTableEvent.getAmountOfPeople();
        this.price = createTableEvent.getPrice();
        this.desciption = createTableEvent.getDesciption();
    }
}