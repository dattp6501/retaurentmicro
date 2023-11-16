package com.dattp.productservice.kafkalisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.dattp.productservice.config.GlobalConfig;
import com.dattp.productservice.dto.RequestBookedTableKafkaDTO;
import com.dattp.productservice.dto.RequestBookingKafkaDTO;
import com.dattp.productservice.entity.TableE;
import com.dattp.productservice.service.TableService;

@Component
public class KafkaBookingListener {
    @Autowired
    private TableService tableService;

    @Autowired
    private KafkaTemplate<String,RequestBookingKafkaDTO> kafkaTemplateBooking;

    @KafkaListener(topics="createBookingTopic", groupId="group1",containerFactory="factoryBooking")
    public void listenCreateBookingTopic(RequestBookingKafkaDTO bookingReuqest){
        // cap nhat,gui lai trang thai ban, mon cua don hang
        for(RequestBookedTableKafkaDTO table : bookingReuqest.getBookedTables()){
            TableE tableSrc = tableService.getById(table.getTableId());
            if(tableSrc==null){//ban khong ton ta
                table.setState(GlobalConfig.NOT_FOUND_STATE);
                continue;
            }
            table.setState(tableSrc.getState());
        }
        kafkaTemplateBooking.send("resultCheckBookingTopic",bookingReuqest);
    }
}