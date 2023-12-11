package com.dattp.paymentservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dattp.paymentservice.entity.HistoryPayment;
import com.dattp.paymentservice.repository.HistoryPaymentRepository;

@Service
public class HistoryPaymentService {
    @Autowired
    private HistoryPaymentRepository historyPaymentRepository;


    public HistoryPayment getByBookingId(Long bookingId){
        return historyPaymentRepository.findByBookingId(bookingId);
    }

    public HistoryPayment save(HistoryPayment historyPayment){
        return historyPaymentRepository.save(historyPayment);
    }
    
    public List<HistoryPayment> getAllByCustomerId(long customerId, Pageable pageable){
        return historyPaymentRepository.findAllByCustomerId(customerId,pageable).toList();
    }
}