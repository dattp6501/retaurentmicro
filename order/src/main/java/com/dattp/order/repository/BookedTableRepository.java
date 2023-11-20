package com.dattp.order.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.order.entity.BookedTable;

public interface BookedTableRepository extends JpaRepository<BookedTable,Long>{
    @Query(value="SELECT * FROM BOOKED_TABLE t "
    +"WHERE :from_<=t.to_ AND t.from_<=:to_", nativeQuery = true)
    public List<BookedTable> findBookedTable(@Param("from_")Date from, @Param("to_")Date to);

    @Modifying
    @Query(value="UPDATE BOOKED_TABLE t "
    +"SET t.state = :state "
    +"WHERE t.id = :id ", nativeQuery = true)
    public int updateState(@Param("id") Long id, @Param("state") Integer state);
}