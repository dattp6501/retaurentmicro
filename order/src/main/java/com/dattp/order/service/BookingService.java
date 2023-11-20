package com.dattp.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.order.config.GlobalConfig;
import com.dattp.order.dto.ResponseBookedDishDTO;
import com.dattp.order.dto.ResponseBookedTableDTO;
import com.dattp.order.dto.ResponseBookingDTO;
import com.dattp.order.entity.Booking;
import com.dattp.order.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookedTableService bookedTableService;
    @Autowired
    private BookedDishService bookedDishService;

    @Transactional
    public Booking save(Booking booking){
        Booking newBooking = bookingRepository.save(booking);
        return newBooking;
    }

    public Booking getByID(Long id){
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getByCustemerID(long custemerID){
        return bookingRepository.getAllByCustomerID(custemerID);
    }

    public void removeById(long id){
        bookingRepository.deleteById(id);
    }

    @Transactional
    public void checkAndUpdateBooking(ResponseBookingDTO bookingResponse){
        // neu don hang khong ton tai
        if(!bookingRepository.existsById(bookingResponse.getId())) return;
        // kiem tra ban dat
        int bookedNumber = bookingResponse.getBookedTables().size();
        for(ResponseBookedTableDTO tableDTO : bookingResponse.getBookedTables()){
            // cap nhat lai trang thai cua ban da dat
            if(!bookedTableService.existsById(tableDTO.getId())) continue;// neu khong co ban ghi nao cua ban dat
            if(tableDTO.getState()==GlobalConfig.NOT_FOUND_STATE){//neu ban khong ton tai
                bookedDishService.removeAllByBookedTableID(tableDTO.getId());//xoa mon truoc
                bookedTableService.removeById(tableDTO.getId());// xoa ban,mon ma ban da dat
                bookedNumber--;
                continue;
            }
            // neu ban ton tai
            bookedTableService.updateState(tableDTO.getId(), tableDTO.getState());//cap nhat lai trang thai cua ban
            // cap nhat lai trang thai cua mon ma ban da dat
            if(tableDTO.getDishs()==null) continue;
            for(ResponseBookedDishDTO dishDTO : tableDTO.getDishs()){//cap nhat trang thai cua mon an
                if(!bookedDishService.existsById(dishDTO.getId())) continue;//neu khong co ban ghi cua ban dat trong database
                if(dishDTO.getState()==GlobalConfig.NOT_FOUND_STATE){//mon khong ton tai
                    bookedDishService.removeById(dishDTO.getId());
                    continue;
                }
                //neu ton tai
                bookedDishService.updateState(dishDTO.getId(), dishDTO.getState());
            }
        }
        if(bookingResponse.getBookedTables()==null || bookedNumber<=0){//neu khong co ban nao duoc dat
            bookingRepository.deleteById(bookingResponse.getId());;
        }else{
            bookingRepository.updateState(bookingResponse.getId() ,GlobalConfig.OK_STATE);
        }
    }
}
