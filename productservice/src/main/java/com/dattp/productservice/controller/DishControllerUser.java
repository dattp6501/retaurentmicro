package com.dattp.productservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.productservice.dto.DishResponseDTO;
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.service.DishService;

@RestController
@RequestMapping("/api/product/user/dish")
public class DishControllerUser {
    @Autowired
    private DishService dishService;

    @GetMapping("/get_dish")
    public ResponseEntity<ResponseDTO> getDishs(Pageable pageable){//page=?&size=?
        List<DishResponseDTO> list = new ArrayList<>();
        dishService.getDishs(pageable).getContent().forEach((d)->{
            DishResponseDTO dishResp = new DishResponseDTO();
            BeanUtils.copyProperties(d, dishResp);
            list.add(dishResp);
        });
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công",
                list
            )
        );
    }
}