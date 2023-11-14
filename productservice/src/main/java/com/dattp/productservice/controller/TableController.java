package com.dattp.productservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dattp.productservice.command.command.CreateListTableCommand;
import com.dattp.productservice.command.command.CreateTableCommand;
import com.dattp.productservice.dto.RequestTableDTO;
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.dto.ResponseTableDTO;
import com.dattp.productservice.entity.TableE;
import com.dattp.productservice.service.TableService;

@RestController
@RequestMapping("/api/table")
public class TableController {
    @Autowired
    private TableService tableService;

    
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping(value = "/save")
    public ResponseTableDTO save(@RequestBody @Valid RequestTableDTO tableR){
        CreateTableCommand command = new CreateTableCommand(
            UUID.randomUUID().toString(),
            tableR.getName(),
            tableR.getAmountOfPeople(), 
            tableR.getPrice(),
            tableR.getDesciption()
        );
        commandGateway.sendAndWait(command);
        return null;
    }
    @PostMapping(value = "/save_with_excel")
    public ResponseEntity<ResponseDTO> save(@RequestParam("file")MultipartFile file) throws IOException{
        System.out.println(file.getOriginalFilename());
        CreateListTableCommand command = new CreateListTableCommand(UUID.randomUUID().toString(), tableService.readXlsxTable(file.getInputStream()));
        commandGateway.sendAndWait(command);
        return null;
    }

    @GetMapping(value = "/get_all")
    public ResponseEntity<List<TableE>> getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(tableService.getAll());
    }
}