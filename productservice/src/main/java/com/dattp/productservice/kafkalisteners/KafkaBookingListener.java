package com.dattp.productservice.kafkalisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.dattp.productservice.config.GlobalConfig;
import com.dattp.productservice.dto.RequestBookedDishKafkaDTO;
import com.dattp.productservice.dto.RequestBookedTableKafkaDTO;
import com.dattp.productservice.dto.RequestBookingKafkaDTO;
import com.dattp.productservice.entity.Dish;
import com.dattp.productservice.entity.TableE;
import com.dattp.productservice.service.DishService;
import com.dattp.productservice.service.TableService;

@Component
public class KafkaBookingListener {
    @Autowired
    private TableService tableService;

    @Autowired
    private DishService dishService;

    @Autowired
    private KafkaTemplate<String,RequestBookingKafkaDTO> kafkaTemplateBooking;

    @KafkaListener(topics="newOrder", groupId="group1",containerFactory="factoryBooking")
    public void listenCreateBookingTopic(RequestBookingKafkaDTO bookingReuqest){
        System.out.println("=========================LISTEN NEW ORDER======================================");
        System.out.println(bookingReuqest.getDate());
        // cap nhat,gui lai trang thai ban, mon cua don hang
        for(RequestBookedTableKafkaDTO table : bookingReuqest.getBookedTables()){
            TableE tableSrc = tableService.getById(table.getTableId());
            if(tableSrc==null){//ban khong ton tai
                table.setState(GlobalConfig.NOT_FOUND_STATE);
                continue;
            }
            table.setState(tableSrc.getState());
            // mon
            if(table.getDishs()==null) continue;
            for(RequestBookedDishKafkaDTO dish : table.getDishs()){
                Dish dishSrc = dishService.getById(dish.getId());
                if(dishSrc==null){
                    dish.setState(GlobalConfig.NOT_FOUND_STATE);
                    continue;
                }
                dish.setState(dishSrc.getState());
            }
        }
        kafkaTemplateBooking.send("checkOrder",bookingReuqest);
        System.out.println("SEND checkOrder SUCCESS");
        System.out.println("=================================================================");
    }
}