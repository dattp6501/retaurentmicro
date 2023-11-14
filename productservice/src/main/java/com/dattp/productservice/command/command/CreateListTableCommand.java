package com.dattp.productservice.command.command;

import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.dattp.productservice.entity.TableE;

import lombok.Getter;

@Getter
public class CreateListTableCommand {
    @TargetAggregateIdentifier
    private String id;
    private List<TableE> tables;
    public CreateListTableCommand(String id, List<TableE> tables) {
        this.id = id;
        this.tables = tables;
    }
}