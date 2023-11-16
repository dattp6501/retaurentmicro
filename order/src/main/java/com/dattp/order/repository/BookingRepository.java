package com.dattp.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dattp.order.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{
    
}