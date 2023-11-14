package com.dattp.order.KafkaListeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dattp.order.entity.Booking;

@Component
public class BookingKafkaListener {
    
    @KafkaListener(topics = "orderTopic", groupId = "Group1", containerFactory = "factoryBooking")
    public void listenerOrderTopic(Booking booking){
        System.out.println("==============================================================");
        System.out.println(booking.getDate());
        System.out.println("=============================================================");
    }
}
