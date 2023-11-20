package com.dattp.order.KafkaListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.dattp.order.dto.BookingResponseDTO;
import com.dattp.order.service.BookingService;

@Component
public class BookingKafkaListener {
    @Autowired
    private BookingService bookingService;

    @KafkaListener(topics = "checkOrder", groupId="group1", containerFactory = "factoryBooking")
    public void listenerResultCreateBookingTopic(@Payload BookingResponseDTO bookingResponse){
        // ket qua nhan vao la trang thai cua ban,mon sau khi productservice kiem tra trang thai cua ban,mon
        System.out.println("=====================LISTEN RESULT CHECK ORDER=========================");
        System.out.println(bookingResponse.getDate());
        // lang nghe su kien kiem tra don dat hang
        bookingService.checkAndUpdateBooking(bookingResponse);
        System.out.println("UPDATE BOOKING SUCCESS");
        System.out.println("=========================================================================");
    }
}
