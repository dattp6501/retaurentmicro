package com.dattp.order.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.dto.ResponseListBookedTableDTO;
import com.dattp.order.repository.BookedTableRepository;

@RestController
@RequestMapping("/api/booked_table")
public class BookedTableController {
    @Autowired
    private BookedTableRepository bookedTableRepository;

    @GetMapping("/get_by_date_from_to")
    public ResponseEntity<ResponseListBookedTableDTO> get(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date from, @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date to){
        return ResponseEntity.ok().body(
            ResponseListBookedTableDTO.builder()
            .code(HttpStatus.OK.value())
            .message("Thành công")
            .bookedtables(bookedTableRepository.findBookedTable(from, to))
            .build()
        );
        
    }
}