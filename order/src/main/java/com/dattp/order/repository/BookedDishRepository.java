package com.dattp.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.order.entity.BookedDish;

public interface BookedDishRepository extends JpaRepository<BookedDish,Long>{
    @Modifying
    @Query(value="UPDATE BOOKED_DISH d "
    +"SET d.state = :state "
    +"WHERE d.id = :id ", nativeQuery = true)
    public int updateState(@Param("id") Long id, @Param("state") Integer state);

    @Modifying
    @Query(value="UPDATE BOOKED_DISH d "
    +"SET d.state = :state "
    +"WHERE d.id = :id ", nativeQuery = true)
    public int updateState(@Param("id") List<Long> ids, @Param("state") Integer state);

    
}
