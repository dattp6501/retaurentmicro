package com.dattp.order.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dattp.order.config.ApplicationConfig;
import com.dattp.order.dto.BookedTableResponseDTO;
import com.dattp.order.dto.BookingResponseDTO;
import com.dattp.order.entity.Booking;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.exception.NotfoundException;
import com.dattp.order.repository.BookedDishRepository;
import com.dattp.order.repository.BookedTableRepository;
import com.dattp.order.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookedTableRepository bookedTableRepository;
    @Autowired
    private BookedDishRepository bookedDishRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private BookedTableService bookedTableService;
    @Autowired
    private KafkaTemplate<String,BookingResponseDTO> bookingKafkaTemplate;

    @Transactional
    public Booking save(Booking booking){
        booking.getBookedTables().forEach(b->{
            if(!bookedTableService.isFreetime(b)){//ban da duoc thue trong khoang thoi gian nay
                throw new BadRequestException("Bàn name = "+b.getName()+" đã được thuê trong khoảng thòi gian này");
            }
        });
        Booking newBooking = bookingRepository.save(booking);
        cartService.deleteAllTable(newBooking.getCustomerId());
        return newBooking;
    }

    public Booking getByID(Long id){
        return bookingRepository.findById(id).orElse(null);
    }

    public Page<Booking> getByCustemerId(long custemerId,Pageable pageable){
        return bookingRepository.getAllByCustomerId(custemerId, pageable);
    }

    public void removeById(long id){
        bookingRepository.deleteById(id);
    }
    
    @Transactional
    public void checkAndUpdateBooking(BookingResponseDTO bookingResponse){
        // neu don hang khong ton tai
        if(!bookingRepository.existsById(bookingResponse.getId())) return;
        //lay danh sach ban co the dat duoc(state != 0) va cap nhat trang thai cua ban, va xoa cac ban khong ton tai
        List<BookedTableResponseDTO> bookedTableSuccess = bookingResponse.getBookedTables().stream()
            .filter(t->bookedTableService.existsById(t.getId()))//lay tat ca ca ban dat ton tai
            .peek((t)->{
                if(t.getState()==ApplicationConfig.NOT_FOUND_STATE){//ban khong ton tai
                    bookedTableService.delete(t.getId());
                }else{//ban ton tai
                    bookedTableService.updateState(t.getId(), t.getState());
                }
            })
            .filter(t->t.getState()!=ApplicationConfig.NOT_FOUND_STATE)//lay cac ban co the dat dau khi cap nhat
            .collect(Collectors.toList());
        if(bookedTableSuccess==null || bookedTableSuccess.size()<=0)
            bookingRepository.deleteById(bookingResponse.getId());
        else bookingRepository.updateState(bookingResponse.getId(), ApplicationConfig.WATING_STATE);
        bookingKafkaTemplate.send("notiOrder",bookingResponse);
    }

    public void delete(Long id){
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking==null) throw new NotfoundException("Đơn hàng không tồn tại");
        bookingRepository.delete(booking);
    }


    // 
    public List<Booking> getAllByFromAndTo(Date from, Date to, Pageable pageable){
        List<Booking> list = bookingRepository.findAllByFromAndTo(from, to, pageable).toList();
        return list;
    }

    @Transactional
    public void cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.getBookedTables().stream().forEach((t)->{
            // delete all dish 
            bookedDishRepository.deleteAll(t.getDishs());
            // update state booked table
            bookedTableRepository.updateState(t.getId(), ApplicationConfig.CANCEL_STATE);
        });
        bookingRepository.updateState(bookingId, ApplicationConfig.CANCEL_STATE);
    }

    @Transactional
    public void confirmBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.getBookedTables().stream().forEach((t)->{
            // update state booked dish
            if(!t.getDishs().isEmpty()){
                t.getDishs().stream().forEach((d)->{
                    bookedDishRepository.updateState(d.getId(), ApplicationConfig.OK_STATE);
                });
            }
            // update state booked table
            bookedTableRepository.updateState(t.getId(), ApplicationConfig.OK_STATE);
        });
        bookingRepository.updateState(bookingId, ApplicationConfig.OK_STATE);
    }
}
