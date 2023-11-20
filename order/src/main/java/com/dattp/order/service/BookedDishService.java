package com.dattp.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.order.repository.BookedDishRepository;

@Service
public class BookedDishService {
    @Autowired
    private BookedDishRepository bookedDishRepository;

    public boolean updateState(Long id, Integer state){
        return bookedDishRepository.updateState(id, state)>0;
    }

    public void removeById(long id){
        bookedDishRepository.deleteById(id);
    }

    public int removeAllByBookedTableID(Long bookedTableId){
        return bookedDishRepository.removeAllByBookedTableID(bookedTableId);
    }

    public boolean existsById(long id){
        return bookedDishRepository.existsById(id);
    }
}
