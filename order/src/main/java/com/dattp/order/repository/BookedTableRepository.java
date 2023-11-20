package com.dattp.order.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.order.entity.BookedTable;

public interface BookedTableRepository extends JpaRepository<BookedTable,Long>{
    // lay tat cac ban dat trong 1 khoang thoi gian(1 ban co the dat nhieu lan trong khoang thoi gian khac nhau)
    @Query(value="SELECT * FROM BOOKED_TABLE t "
    +"WHERE NOT (:to_<t.from_ OR t.to_<:from_) ", nativeQuery = true)
    public List<BookedTable> findBookedTable(@Param("from_") Date from, @Param("to_")Date to);

    // lay tat cac ban dat trong 1 khoang thoi gian cua 1 ban(hay lay cac khung thoi gian da dat cua ban)
    @Query(value="SELECT * FROM BOOKED_TABLE t "
    +"WHERE NOT (:to_<t.from_ OR t.to_<:from_) AND t.table_id=:table_id", nativeQuery = true)
    public List<BookedTable> findBookedTable(@Param("from_") Date from, @Param("to_")Date to, @Param("table_id") Long tableID);

    // cap nhat trang thai cua ban dat
    @Modifying
    @Query(value="UPDATE BOOKED_TABLE t "
    +"SET t.state = :state "
    +"WHERE t.id = :id ", nativeQuery = true)
    public int updateState(@Param("id") Long id, @Param("state") Integer state);
}