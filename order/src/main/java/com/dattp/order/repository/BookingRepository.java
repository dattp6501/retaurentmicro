package com.dattp.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.order.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{
    @Modifying
    @Query(value="UPDATE BOOKING bk SET bk.state = :state WHERE bk.id = :id", nativeQuery = true)
    public int updateState(@Param("id") Long id,@Param("state") Integer state);

    @Query(value="SELECT * FROM BOOKING bk WHERE bk.customer_id=:customer_id", nativeQuery = true)
    public List<Booking> getAllByCustomerID(@Param("customer_id") Long customerID);
}