package com.dattp.productservice.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dattp.productservice.config.GlobalConfig;
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.dto.TableRequestDTO;
import com.dattp.productservice.dto.TableResponseDTO;
import com.dattp.productservice.entity.TableE;
import com.dattp.productservice.service.TableService;

@RestController
@RequestMapping("/api/product/manager/table")
public class TableControllerManager {
    @Autowired
    private TableService tableService;

    @PostMapping(value = "/save")
    @RolesAllowed({"ROLE_ADMIN","ROLE_PRODUCT_NEW"})
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid TableRequestDTO tableR){
        TableE table = new TableE();
        table.setState(GlobalConfig.DEFAULT_STATE);
        table.setName(tableR.getName());
        table.setAmountOfPeople(tableR.getAmountOfPeople());
        table.setPrice(tableR.getPrice());
        table.setDesciption(tableR.getDesciption());
        table = tableService.saveTable(table);
        TableResponseDTO tableDTO = new TableResponseDTO();
        BeanUtils.copyProperties(table, tableDTO);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công", 
                tableDTO
            )
        );
    }
    
    @PostMapping(value = "/save_with_excel")
    @RolesAllowed({"ROLE_ADMIN","ROLE_PRODUCT_NEW"})
    public ResponseEntity<ResponseDTO> save(@RequestParam("file")MultipartFile file) throws IOException{
        List<TableE> newList = tableService.save(tableService.readXlsxTable(file.getInputStream()));
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công", 
                newList
            )
        );
    }
}
