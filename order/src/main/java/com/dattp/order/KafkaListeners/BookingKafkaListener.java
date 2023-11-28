package com.dattp.order.KafkaListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dattp.order.dto.BookingResponseDTO;
import com.dattp.order.service.BookingService;

@Component
public class BookingKafkaListener {
    @Autowired
    private BookingService bookingService;

    @KafkaListener(topics = "checkOrder", groupId="group1", containerFactory = "factoryBooking")
    @Transactional
    public void listenerResultCreateBookingTopic(@Payload BookingResponseDTO bookingResponse){
        // ket qua nhan vao la thong tin cua ban sau khi productservice kiem tra 
        // lang nghe su kien kiem tra don dat hang
        System.out.println("========================= LISTEN checkOrder  ====================");
        bookingService.checkAndUpdateBooking(bookingResponse);
    }
}
