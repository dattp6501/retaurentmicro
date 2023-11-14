package com.dattp.productservice.command.aggregate;

import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.dattp.productservice.command.command.CreateListTableCommand;
import com.dattp.productservice.command.event.CreateListTableEvent;
import com.dattp.productservice.entity.TableE;

import lombok.Getter;

@Aggregate
@Getter
public class ListTableAggregate {
    @AggregateIdentifier
    private String id;
    private List<TableE> tables;
    public ListTableAggregate(){}
    // create table with file
    @CommandHandler
    public ListTableAggregate(CreateListTableCommand command){
        CreateListTableEvent createListTableEvent = new CreateListTableEvent();
        createListTableEvent.setId(command.getId());
        createListTableEvent.setTables(command.getTables());
        AggregateLifecycle.apply(createListTableEvent);
    }
    @EventSourcingHandler
    public void on(CreateListTableEvent createListTableEvent){
        this.id = createListTableEvent.getId();
        this.tables = createListTableEvent.getTables();
    }
}
