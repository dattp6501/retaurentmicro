package com.dattp.paymentservice.kafkalistener;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dattp.paymentservice.config.ApplicationConfig;
import com.dattp.paymentservice.dto.kafka.BookingRequestKafkaDTO;
import com.dattp.paymentservice.entity.Booking;
import com.dattp.paymentservice.service.BookingService;

@Component
public class PaymentOrderKafkaListener {
    @Autowired
    private BookingService bookingService;
    // sent after confirmation by the manager(orderservice send)
    @KafkaListener(topics = "createPaymentOrder", groupId = "group1", containerFactory = "factoryBooking")
    @Transactional
    public void listenerCreatePaymentOrder(BookingRequestKafkaDTO req){
        Booking booking = new Booking();
        BeanUtils.copyProperties(req, booking);
        booking.setState(ApplicationConfig.UNPAID_STATE);
        bookingService.save(booking);
    }
}