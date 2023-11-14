package com.dattp.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.order.entity.BookedTable;
import com.dattp.order.repository.BookedTableRepository;

@Service
public class BookedTableService {
    @Autowired
    private BookedTableRepository bookedTableRepository;

    public void getBookedTable(Date from, Date to){
        System.out.println(bookedTableRepository.findBookedTable(from, to).size());
    }

    public List<BookedTable> saveBookedTable(List<BookedTable> bookedTables){
        return bookedTableRepository.saveAll(bookedTables);
    }

    
}
