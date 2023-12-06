package com.dattp.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.paymentservice.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{

    @Modifying
    @Query(
        value = "UPDATE BOOKING b SET b.state=:state WHERE b.id=:id", nativeQuery = true
    )
    public int updateState(@Param("state") int state, @Param("id") long id);
}