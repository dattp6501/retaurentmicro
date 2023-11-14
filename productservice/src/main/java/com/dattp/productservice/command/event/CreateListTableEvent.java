package com.dattp.productservice.command.event;

import java.util.List;

import com.dattp.productservice.entity.TableE;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateListTableEvent {
    private String id;
    private List<TableE> tables;
    public CreateListTableEvent(){}
}
