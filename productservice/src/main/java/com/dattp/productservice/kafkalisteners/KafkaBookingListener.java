package com.dattp.productservice.kafkalisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.dattp.productservice.config.ApplicationConfig;
import com.dattp.productservice.dto.kafka.BookedTableRequestKafkaDTO;
import com.dattp.productservice.dto.kafka.BookingRequestKafkaDTO;
import com.dattp.productservice.entity.TableE;
import com.dattp.productservice.service.TableService;

@Component
public class KafkaBookingListener {
    @Autowired
    private TableService tableService;

    @Autowired
    private KafkaTemplate<String,BookingRequestKafkaDTO> kafkaTemplateBooking;

    @KafkaListener(topics="newOrder", groupId="group1",containerFactory="factoryBooking")
    public void listenCreateBookingTopic(BookingRequestKafkaDTO bookingReuqest){
        System.out.println("=========================LISTEN NEW ORDER======================================");
        System.out.println(bookingReuqest.getDate());
        // cap nhat,gui lai thong tin ban
        for(BookedTableRequestKafkaDTO table : bookingReuqest.getBookedTables()){
            TableE tableSrc = tableService.getById(table.getTableId());
            if(tableSrc==null){//ban khong ton tai
                table.setState(ApplicationConfig.NOT_FOUND_STATE);
                continue;
            }
            table.setState(tableSrc.getState());
            table.setPrice(tableSrc.getPrice());
        }
        kafkaTemplateBooking.send("checkOrder",bookingReuqest);
        System.out.println("SEND checkOrder SUCCESS");
        System.out.println("=================================================================");
    }
}