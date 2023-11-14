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

import com.dattp.productservice.command.command.CreateDishCommand;
import com.dattp.productservice.command.command.CreateListDishCommand;
import com.dattp.productservice.dto.RequestDishDTO;
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.dto.ResponseDishDTO;
import com.dattp.productservice.entity.Dish;
import com.dattp.productservice.service.DishService;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/save")
    public ResponseDishDTO save(@RequestBody @Valid RequestDishDTO dish){
        CreateDishCommand command = new CreateDishCommand(
            UUID.randomUUID().toString(), 
            dish.getName(), 
            dish.getPrice(), 
            dish.getDiscription()
        );
        commandGateway.sendAndWait(command);
        return null;
    }

    @PostMapping("/save_with_excel")
    public ResponseEntity<ResponseDTO> save(@RequestParam("file") MultipartFile file) throws IOException{
        System.out.println(file.getOriginalFilename());
        CreateListDishCommand command = new CreateListDishCommand(UUID.randomUUID().toString(), dishService.readXlsxDish(file.getInputStream()));
        commandGateway.sendAndWait(command);
        return null;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Dish>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(dishService.getAll());
    }
}