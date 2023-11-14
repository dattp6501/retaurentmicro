package com.dattp.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.dto.RequestBookedDishDTO;
import com.dattp.order.dto.RequestBookedTableDTO;
import com.dattp.order.dto.RequestBookingDTO;
import com.dattp.order.dto.ResponseBookingDTO;
import com.dattp.order.dto.ResponseListBookingDTO;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping(value="/save")
    public ResponseEntity<Booking> save(@RequestBody @Valid RequestBookingDTO bookingR) throws BadRequestException {
        Booking booking = new Booking();
        booking.setDate(new Date());
        booking.setCustomerId(bookingR.getCustomerID());
        booking.setDescription(bookingR.getDescription());
        List<BookedTable> tables = new ArrayList<>();
        for(RequestBookedTableDTO b : bookingR.getTables()){
            BookedTable table = new BookedTable();
            table.setTableId(b.getTableId());
            table.setFrom(b.getFrom());
            table.setTo(b.getTo());
            table.setPrice(b.getPrice());
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
                    dish.setDishID(d.getDishId());
                    dish.setPrice(d.getPrice());
                    dish.setTotal(d.getTotal());
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
        return ResponseEntity.ok().body(bookingService.save(booking));
    }

    @GetMapping("/get_all_booking")
    public ResponseEntity<ResponseListBookingDTO> getByCustemerID(@RequestHeader("access_token") String accessToken){
        return ResponseEntity.ok().body(
            ResponseListBookingDTO.builder()
            .code(HttpStatus.OK.value())
            .message("Thành công")
            .bookings(bookingService.getByCustemerID(1))
            .build()
        );
    }

    @GetMapping("/get_booking_by_id")
    public ResponseEntity<ResponseBookingDTO> getByID(@RequestParam(value="id") long bookingID){
        return ResponseEntity.ok().body(
            ResponseBookingDTO.builder()
            .code(HttpStatus.OK.value())
            .message("Thành công")
            .booking(bookingService.getByID(bookingID))
            .build()
        );
    }
}