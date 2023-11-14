package com.dattp.productservice.command.event;

import java.io.IOException;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dattp.productservice.entity.TableE;
import com.dattp.productservice.service.TableService;

@Component
public class TableEventHandler {
    @Autowired
    private TableService tableService;

    @EventHandler
    public void on(CreateTableEvent createTableEvent){
        TableE table = new TableE();
        BeanUtils.copyProperties(createTableEvent, table);
        tableService.saveTable(table);
    }

    @EventHandler
    public void on(CreateListTableEvent createListTableEvent) throws IOException{
        tableService.save(createListTableEvent.getTables());
    }
}
