package com.dattp.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.config.ApplicationConfig;
import com.dattp.order.dto.BookedDishRequestDTO;
import com.dattp.order.dto.BookedTableRequestDTO;
import com.dattp.order.dto.BookingRequestDTO;
import com.dattp.order.dto.BookedDishResponseDTO;
import com.dattp.order.dto.BookedTableResponseDTO;
import com.dattp.order.dto.BookingResponseDTO;
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


@RestController
@RequestMapping("/api/booking")
public class BookingController {
    // @Autowired
    // private KafkaTemplate<String,String> kafkaTemplateString;

    @Autowired
    private KafkaTemplate<String,BookingResponseDTO> kafkaTemplateBooking;

    @Autowired
    BookingService bookingService;

    @PostMapping(value="/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid BookingRequestDTO bookingR) throws BadRequestException {
        Booking booking = new Booking();
        BeanUtils.copyProperties(bookingR, booking);
        booking.setState(ApplicationConfig.DEFAULT_STATE);
        booking.setDate(new Date());
        List<BookedTable> tables = new ArrayList<>();
        for(BookedTableRequestDTO b : bookingR.getBookedTables()){
            BookedTable table = new BookedTable();
            BeanUtils.copyProperties(b, table);
            table.setState(ApplicationConfig.DEFAULT_STATE);
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
                for(BookedDishRequestDTO d : b.getDishs()){
                    BookedDish dish = new BookedDish();
                    BeanUtils.copyProperties(d, dish);
                    dish.setState(ApplicationConfig.DEFAULT_STATE);
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
        BookingResponseDTO responseBookingDTO = new BookingResponseDTO();
        BeanUtils.copyProperties(booking, responseBookingDTO);
        // ban
        responseBookingDTO.setBookedTables(new ArrayList<>());
        for(BookedTable newTable : booking.getBookedTables()){
            BookedTableResponseDTO tableDTO = new BookedTableResponseDTO();
            BeanUtils.copyProperties(newTable, tableDTO);
            // mon
            if(newTable.getDishs()!=null){
                tableDTO.setDishs(new ArrayList<>());
                for(BookedDish newDish : newTable.getDishs()){
                    BookedDishResponseDTO dishDTO = new BookedDishResponseDTO();
                    BeanUtils.copyProperties(newDish, dishDTO);
                    tableDTO.getDishs().add(dishDTO);
                }
            }
            responseBookingDTO.getBookedTables().add(tableDTO);
        }
        kafkaTemplateBooking.send("newOrder",responseBookingDTO);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                responseBookingDTO
            )
        );
    }

    @GetMapping("/get_all_booking")
    public ResponseEntity<ResponseDTO> getByCustemerID(){
        List<BookingResponseDTO> list = new ArrayList<>();
        List<Booking> listBK = bookingService.getByCustemerID(1);
        if(listBK!=null && !listBK.isEmpty()){
            for(Booking bk : listBK){
                BookingResponseDTO bkDTO = new BookingResponseDTO();
                BeanUtils.copyProperties(bk, bkDTO);
                list.add(bkDTO);
            }
        }
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công",
                list
            )
        );
    }

    @GetMapping("/get_booking_by_id")
    public ResponseEntity<BookingResponseDTO> getByID(@RequestParam(value="id") long bookingID){
        return null;
    }
}