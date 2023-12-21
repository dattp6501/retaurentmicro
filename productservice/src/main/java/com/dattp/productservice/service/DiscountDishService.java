package com.dattp.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dattp.productservice.entity.DiscountDIsh;
import com.dattp.productservice.repository.DiscountDishRepository;
import com.dattp.productservice.repository.DishRepository;

@Service
public class DiscountDishService {
    @Autowired
    private DiscountDishRepository discountDishRepository;
    @Autowired
    private DishRepository dishRepository;


    public DiscountDIsh save(DiscountDIsh discountDIsh) throws Exception{
        if(dishRepository.findById(discountDIsh.getDish().getId()).isEmpty()) throw new Exception("Món ăn không tồn tại");
        if(discountDishRepository.existsById(discountDIsh.getCode())) throw new Exception("Mã giảm giá "+discountDIsh.getCode()+" đã tồn tại");
        return discountDishRepository.save(discountDIsh);
    }
    
    public List<DiscountDIsh> saveAll(List<DiscountDIsh> list){
        return discountDishRepository.saveAll(list);
    }

    public List<DiscountDIsh> getAllDiscountDish(Pageable pageable){
        return discountDishRepository.findAll(pageable).toList();
    }
}