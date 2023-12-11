package com.dattp.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dattp.paymentservice.entity.Booking;
import com.dattp.paymentservice.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking save(Booking booking){
        return bookingRepository.save(booking);
    }

    public Booking getById(long bookingId){
        return bookingRepository.findById(bookingId).orElse(null);
    }

    @Transactional
    public int updateState(long id, int state){
        return bookingRepository.updateState(state, id);
    }
}
