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

    public List<BookedTable> getBookedTable(Date from, Date to){
        return bookedTableRepository.findBookedTable(from, to);
    }

    public List<BookedTable> saveBookedTable(List<BookedTable> bookedTables){
        return bookedTableRepository.saveAll(bookedTables);
    }

    public boolean updateState(long id, int state){
        return bookedTableRepository.updateState(id, state)>0;
    }

    
}
