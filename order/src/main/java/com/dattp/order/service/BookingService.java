package com.dattp.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dattp.order.config.ApplicationConfig;
import com.dattp.order.dto.BookedDishResponseDTO;
import com.dattp.order.dto.BookedTableResponseDTO;
import com.dattp.order.dto.BookingResponseDTO;
import com.dattp.order.entity.BookedTable;
import com.dattp.order.entity.Booking;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.exception.NotfoundException;
import com.dattp.order.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookedTableService bookedTableService;
    @Autowired
    private BookedDishService bookedDishService;
    @Autowired
    private KafkaTemplate<String,BookingResponseDTO> bookingKafkaTemplate;

    @Transactional
    public Booking save(Booking booking){
        booking.getBookedTables().forEach(b->{
            if(!bookedTableService.isFreetime(b)){//ban da duoc thue trong khoang thoi gian nay
                throw new BadRequestException("Bàn ID = "+b.getTableId()+" đã được thuê trong khoảng thòi gian này");
            }
        });
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

    // @Transactional
    // public void checkAndUpdateBooking(BookingResponseDTO bookingResponse){
    //     // neu don hang khong ton tai
    //     if(!bookingRepository.existsById(bookingResponse.getId())) return;
    //     // kiem tra ban dat
    //     List<BookedTableResponseDTO> bookedTableError = new ArrayList<>();//danh sach ban khong dat duoc
    //     for(BookedTableResponseDTO tableDTO : bookingResponse.getBookedTables()){
    //         // cap nhat lai trang thai cua ban da dat
    //         if(!bookedTableService.existsById(tableDTO.getId())) continue;// neu khong co ban ghi nao cua ban dat
    //         if(tableDTO.getState()==ApplicationConfig.NOT_FOUND_STATE){//neu ban khong ton tai
    //             bookedDishService.removeAllByBookedTableID(tableDTO.getId());//xoa mon truoc
    //             bookedTableService.removeById(tableDTO.getId());// xoa ban,mon ma ban da dat
    //             bookedTableError.add(tableDTO);//them ban vao danh sach ban loi
    //             continue;
    //         }
    //         // neu ban ton tai
    //         bookedTableService.updateState(tableDTO.getId(), tableDTO.getState());//cap nhat lai trang thai cua ban
    //         // cap nhat lai trang thai cua mon ma ban da dat
    //         List<BookedDishResponseDTO> bookedDishErrors = new ArrayList<>();//danh sach mon khong dat duoc
    //         if(tableDTO.getDishs()==null) continue;
    //         for(BookedDishResponseDTO dishDTO : tableDTO.getDishs()){//cap nhat trang thai cua mon an
    //             if(!bookedDishService.existsById(dishDTO.getId())) continue;//neu khong co ban ghi cua ban dat trong database
    //             if(dishDTO.getState()==ApplicationConfig.NOT_FOUND_STATE){//mon khong ton tai
    //                 bookedDishService.removeById(dishDTO.getId());
    //                 bookedDishErrors.add(dishDTO);
    //                 continue;
    //             }
    //             //neu ton tai
    //             bookedDishService.updateState(dishDTO.getId(), dishDTO.getState());
    //         }
    //         tableDTO.getDishs().removeAll(bookedDishErrors);
    //     }
    //     bookingResponse.getBookedTables().removeAll(bookedTableError);
    //     if(bookingResponse.getBookedTables()==null || bookingResponse.getBookedTables().size()<=0){//neu khong co ban nao duoc dat
    //         bookingRepository.deleteById(bookingResponse.getId());
    //     }else{
    //         bookingRepository.updateState(bookingResponse.getId() ,ApplicationConfig.OK_STATE);
    //         bookingResponse.setState(ApplicationConfig.OK_STATE);
    //     }
    //     bookingKafkaTemplate.send("notiOrder",bookingResponse);
        
    // }
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
            .filter(t->t.getState()!=ApplicationConfig.NOT_FOUND_STATE)//lay cac ban co the dat
            .peek((t)->{
                if(t.getDishs()!=null){
                    t.setDishs(t.getDishs().stream()
                        .filter(d->bookedDishService.existsById(d.getId()))//loc ra tat ca cac mon dat ton tai
                        .peek((d)->{
                            if(d.getState()==ApplicationConfig.NOT_FOUND_STATE){//neu mon khong ton tai
                                bookedDishService.removeById(d.getId());
                            }else{
                                bookedDishService.updateState(d.getId(), d.getState());
                            }
                        }).collect(Collectors.toList())
                    );
                }
            })
            .collect(Collectors.toList());
        if(bookingResponse.getBookedTables()==null ||bookingResponse.getBookedTables().size()<=0)
            bookingRepository.deleteById(bookingResponse.getId());
        else bookingRepository.updateState(bookingResponse.getId(), ApplicationConfig.OK_STATE);
        
        bookingKafkaTemplate.send("notiOrder",bookingResponse);
    }

    public void delete(Long id){
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking==null) throw new NotfoundException("Đơn hàng không tồn tại");
        bookingRepository.delete(booking);
    }
}
