package com.dattp.productservice.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.dattp.productservice.dto.DishRequestDTO;
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.dto.DishResponseDTO;
import com.dattp.productservice.entity.Dish;
import com.dattp.productservice.service.DishService;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid DishRequestDTO dishR){
        Dish dish = new Dish();
        dish.setName(dishR.getName());
        dish.setPrice(dishR.getPrice());
        dish.setDiscription(dishR.getDiscription());
        dish = dishService.save(dish);
        DishResponseDTO dishDTO = new DishResponseDTO();
        BeanUtils.copyProperties(dish, dishDTO);
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(),
            "Thành công", 
            dishDTO)
        );
    }

    @PostMapping("/save_with_excel")
    public ResponseEntity<ResponseDTO> save(@RequestParam("file") MultipartFile file) throws IOException{
        List<Dish> dishs = dishService.save(dishService.readXlsxDish(file.getInputStream()));
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                dishs
            )
        );
    }

    @GetMapping("/get_all")
    public ResponseEntity<ResponseDTO> getAll(){
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                dishService.getAll()
            )
        );
    }
}