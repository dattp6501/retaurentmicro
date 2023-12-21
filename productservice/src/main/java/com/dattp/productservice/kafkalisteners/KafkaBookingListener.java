package com.dattp.productservice.kafkalisteners;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.dattp.productservice.config.ApplicationConfig;
import com.dattp.productservice.dto.kafka.BookedDishRequestKafkaDTO;
import com.dattp.productservice.dto.kafka.BookedTableRequestKafkaDTO;
import com.dattp.productservice.dto.kafka.BookingRequestKafkaDTO;
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
    private KafkaTemplate<String,BookingRequestKafkaDTO> kafkaTemplateBooking;

    @KafkaListener(topics="newOrder", groupId="group1",containerFactory="factoryBooking")
    public void listenCreateBookingTopic(BookingRequestKafkaDTO bookingRequest){
        // cap nhat,gui lai thong tin ban
        for(BookedTableRequestKafkaDTO table : bookingRequest.getBookedTables()){
            TableE tableSrc = tableService.getById(table.getTableId());
            if(tableSrc==null){//ban khong ton tai
                table.setState(ApplicationConfig.NOT_FOUND_STATE);
                continue;
            }
            table.setState(tableSrc.getState());
            table.setPrice(tableSrc.getPrice());
        }
        kafkaTemplateBooking.send("checkOrder",bookingRequest);
    }

    // listen message checkBookedDish topic to check info dish
    @KafkaListener(topics="checkBookedDish", groupId="group1",containerFactory="factoryBooking")
    public void listenCheckBookedDishTopic(BookingRequestKafkaDTO bookingRequest){
        bookingRequest.setDiscount(0);
        // check dish
        for(BookedDishRequestKafkaDTO bdRq : bookingRequest.getDishs()){
            // Get dish information and active discounts
            Dish dishSrc = dishService.getById(bdRq.getDishId(), false);
            if(dishSrc == null){
                bdRq.setState(ApplicationConfig.NOT_FOUND_STATE);
                continue;
            }
            bdRq.setName(dishSrc.getName());
            bdRq.setPrice(dishSrc.getPrice());
            bdRq.setState(dishSrc.getState());
            bookingRequest.setDiscount(bookingRequest.getDiscount()+dishSrc.getAmountDiscount());
        }
        kafkaTemplateBooking.send("resultCheckBookedDish",bookingRequest);
    }
}