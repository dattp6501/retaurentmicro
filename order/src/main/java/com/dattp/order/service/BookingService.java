package com.dattp.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.order.entity.Booking;
import com.dattp.order.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public Booking save(Booking booking){
        Booking newBooking = bookingRepository.save(booking);
        return newBooking;
    }

    public Booking getByID(Long id){
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getByCustemerID(long custemerID){
        return bookingRepository.findAll();
    }
}
