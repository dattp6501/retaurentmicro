package com.dattp.paymentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.paymentservice.entity.HistoryPayment;

public interface HistoryPaymentRepository extends JpaRepository<HistoryPayment,Long>{
    @Query(
        value = "SELECT * FROM HISTORY_PAYMENT hp WHERE hp.booking_id=:booking_id ", nativeQuery = true
    )
    public HistoryPayment findByBookingId(@Param("booking_id") long bookingId);

    @Query(
        value = "SELECT * FROM HISTORY_PAYMENT hp WHERE hp.customer_id=:customer_id ", nativeQuery = true
    )
    public Page<HistoryPayment> findAllByCustomerId(@Param("customer_id") long customerId, Pageable pageable);
}