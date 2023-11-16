package com.dattp.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.config.GlobalConfig;
import com.dattp.order.dto.RequestBookedDishDTO;
import com.dattp.order.dto.RequestBookedTableDTO;
import com.dattp.order.dto.RequestBookingDTO;
import com.dattp.order.dto.ResponseBookedDishDTO;
import com.dattp.order.dto.ResponseBookedTableDTO;
import com.dattp.order.dto.ResponseBookingDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.entity.BookedDish;
import com.dattp.order.entity.BookedTable;
import com.dattp.order.entity.Booking;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.service.BookingService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/booking")
public class BookingController {
    // @Autowired
    // private KafkaTemplate<String,String> kafkaTemplateString;

    @Autowired
    private KafkaTemplate<String,ResponseBookingDTO> kafkaTemplateBooking;

    @Autowired
    BookingService bookingService;

    @PostMapping(value="/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid RequestBookingDTO bookingR) throws BadRequestException {
        Booking booking = new Booking();
        booking.setState(GlobalConfig.DEFAULT_STATE);
        booking.setDate(new Date());
        booking.setCustomerId(bookingR.getCustomerID());
        booking.setDescription(bookingR.getDescription());
        List<BookedTable> tables = new ArrayList<>();
        for(RequestBookedTableDTO b : bookingR.getTables()){
            BookedTable table = new BookedTable();
            table.setState(GlobalConfig.DEFAULT_STATE);
            table.setTableId(b.getTableId());
            table.setFrom(b.getFrom());
            table.setTo(b.getTo());
            table.setPrice(b.getPrice());
            table.setBooking(booking);
            if(tables.contains(table)){
                throw new BadRequestException("Bàn có id = "+table.getTableId()+" bị trùng");
            }
            if(table.getFrom().compareTo(table.getTo())>=0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                throw new BadRequestException("Bàn có id = "+table.getTableId()+" có thời gian bắt đầu("+sdf.format(table.getFrom())+") >= thời gian kết thúc("+sdf.format(table.getTo()));
            }
            // dish
            if(b.getDishs()!=null){
                List<BookedDish> dishs = new ArrayList<>();
                for(RequestBookedDishDTO d : b.getDishs()){
                    BookedDish dish = new BookedDish();
                    dish.setState(GlobalConfig.DEFAULT_STATE);
                    dish.setDishID(d.getDishId());
                    dish.setPrice(d.getPrice());
                    dish.setTotal(d.getTotal());
                    dish.setTable(table);
                    if(dishs.contains(dish)){
                        throw new BadRequestException("Món ăn có id = "+dish.getDishID()+" bị trùng");
                    }
                    dishs.add(dish);
                }
                table.setDishs(dishs);
            }
            // 
            tables.add(table);
        }
        booking.setBookedTables(tables);
        booking = bookingService.save(booking);
        // response
        // phieu dat ban
        ResponseBookingDTO responseBookingDTO = new ResponseBookingDTO();
        BeanUtils.copyProperties(booking, responseBookingDTO);
        // ban
        responseBookingDTO.setBookedTables(new ArrayList<>());
        for(BookedTable newTable : booking.getBookedTables()){
            ResponseBookedTableDTO tableDTO = new ResponseBookedTableDTO();
            BeanUtils.copyProperties(newTable, tableDTO);
            // mon
            if(newTable.getDishs()!=null){
                tableDTO.setDishs(new ArrayList<>());
                for(BookedDish newDish : newTable.getDishs()){
                    ResponseBookedDishDTO dishDTO = new ResponseBookedDishDTO();
                    BeanUtils.copyProperties(newDish, dishDTO);
                    tableDTO.getDishs().add(dishDTO);
                }
            }
            responseBookingDTO.getBookedTables().add(tableDTO);
        }
        kafkaTemplateBooking.send("createBookingTopic",responseBookingDTO);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                responseBookingDTO
            )
        );
    }

    @GetMapping("/get_all_booking")
    public ResponseEntity<ResponseDTO> getByCustemerID(@RequestHeader("access_token") String accessToken){
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công",
                bookingService.getByCustemerID(1) 
            )
        );
    }

    @GetMapping("/get_booking_by_id")
    public ResponseEntity<ResponseBookingDTO> getByID(@RequestParam(value="id") long bookingID){
        return null;
    }
}