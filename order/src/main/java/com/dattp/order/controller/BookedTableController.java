package com.dattp.order.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.dto.BookedTableResponseDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.service.BookedTableService;

@RestController
@RequestMapping("/api/booking/booked_table")
public class BookedTableController {
    @Autowired
    private BookedTableService bookedTableService;

    @GetMapping("/get_by_date_from_to")
    public ResponseEntity<ResponseDTO> get(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date from, @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date to){
        List<BookedTableResponseDTO> bookedTables = new ArrayList<>();
        BeanUtils.copyProperties(bookedTableService.getBookedTable(from, to), bookedTables);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công", 
                bookedTables
            )
        );
    }
}