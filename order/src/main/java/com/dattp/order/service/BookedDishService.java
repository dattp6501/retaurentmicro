package com.dattp.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.order.repository.BookedDishRepository;

@Service
public class BookedDishService {
    @Autowired
    private BookedDishRepository bookedDishRepository;

    public boolean updateState(long id, int state){
        return bookedDishRepository.updateState(id, state)>0;
    }
}
