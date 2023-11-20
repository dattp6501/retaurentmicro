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

    public boolean updateState(Long id, Integer state){
        return bookedTableRepository.updateState(id, state)>0;
    }

    public void removeById(Long id){
        bookedTableRepository.deleteById(id);
    }

    public boolean existsById(long id){
        return bookedTableRepository.existsById(id);
    }

    // neu co ban da duoc dat trong khoang thoi gian nay thi tra ve true, nguoc lai tra ve false
    public boolean isFreetime(BookedTable bookedTable){
        List<BookedTable> list = bookedTableRepository.findBookedTable(bookedTable.getFrom(),bookedTable.getTo(), bookedTable.getTableId());
        if(list==null) return true;
        return list.size()<=0;
    }
}
