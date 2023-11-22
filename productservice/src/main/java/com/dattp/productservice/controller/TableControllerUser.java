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
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.dto.TableResponseDTO;
import com.dattp.productservice.service.TableService;

@RestController
@RequestMapping("/api/product/user/table")
public class TableControllerUser {
    @Autowired
    private TableService tableService;

    @GetMapping(value = "/get_table")
    public ResponseEntity<ResponseDTO> getAllTable(Pageable pageable){
        List<TableResponseDTO> list = new ArrayList<>();
        tableService.getAll(pageable).getContent().forEach((t)->{
            TableResponseDTO tableResp = new TableResponseDTO();
            BeanUtils.copyProperties(t, tableResp);
            list.add(tableResp);
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