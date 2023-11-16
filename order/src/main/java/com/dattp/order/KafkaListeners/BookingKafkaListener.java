package com.dattp.order.KafkaListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.dattp.order.dto.ResponseBookedDishDTO;
import com.dattp.order.dto.ResponseBookedTableDTO;
import com.dattp.order.dto.ResponseBookingDTO;
import com.dattp.order.service.BookedDishService;
import com.dattp.order.service.BookedTableService;

@Component
public class BookingKafkaListener {
    @Autowired
    private BookedTableService bookedTableService;
    @Autowired
    private BookedDishService bookedDishService;

    @KafkaListener(topics = "resultCheckBookingTopic", groupId="group1", containerFactory = "factoryBooking")
    public void listenerCreateBookingTopic(@Payload ResponseBookingDTO bookingRequest){
        // lang nghe su kien kiem tra don dat hang
        for(ResponseBookedTableDTO tableDTO : bookingRequest.getBookedTables()){
            // cap nhat lai trang thai cua ban da dat
            bookedTableService.updateState(tableDTO.getId(), tableDTO.getState());
            // if(tableDTO.getDishs()==null) continue;
            // for(ResponseBookedDishDTO dishDTO : tableDTO.getDishs()){//cap nhat trang thai cua mon an
            //     bookedDishService.updateState(dishDTO.getId(), dishDTO.getState());
            // }
        }
        System.out.println("UPDATE STATE BOOKING SUCCESS");
        
    }

    // @KafkaListener(topics = "testTopic", groupId="group2", containerFactory = "factoryString")
    // public void listenerCreateBookingTopic(String data){
    //     System.out.println("=============================================================");
    //     System.out.println(data);
    //     System.out.println("=============================================================");
    // }
}
