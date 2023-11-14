package com.dattp.order.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dattp.order.entity.BookedTable;

public interface BookedTableRepository extends JpaRepository<BookedTable,Long>{
    @Query(value="SELECT * FROM BOOKED_TABLE t "
    +"WHERE ?1<=t.to_ AND t.from_<=?2", nativeQuery = true)
    public List<BookedTable> findBookedTable(Date from, Date to);

    
}