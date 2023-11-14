package com.dattp.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dattp.order.entity.Booking;
import com.dattp.order.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplateString;

    @Autowired
    private KafkaTemplate<String,Booking> kafkaTemplateBooking;

    @Transactional
    public Booking save(Booking booking){
        kafkaTemplateBooking.send("orderTopic",booking);
        return bookingRepository.save(booking);
    }

    public Booking getByID(Long id){
        kafkaTemplateString.send("orderservice", "xin chao "+id);
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getByCustemerID(long custemerID){
        return null;
        // return bookingRepository.findByCustomerId(custemerID);
    }
}
