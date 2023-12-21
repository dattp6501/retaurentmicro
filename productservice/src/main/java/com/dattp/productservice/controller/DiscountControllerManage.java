package com.dattp.productservice.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.productservice.dto.DiscountDishRequestDTO;
import com.dattp.productservice.dto.ResponseDTO;
import com.dattp.productservice.entity.ConditionDiscountDish;
import com.dattp.productservice.entity.DiscountDIsh;
import com.dattp.productservice.entity.Dish;
import com.dattp.productservice.service.DiscountDishService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/product/manage/discount")
public class DiscountControllerManage {
    @Autowired
    private DiscountDishService discountDishService;




    @PostMapping("/add_discount_dish")
    @RolesAllowed({"ROLE_ADMIN","ROLE_PRODUCT_UPDATE"})
    @Transactional
    public ResponseEntity<ResponseDTO> postMethodName(@RequestBody @Valid DiscountDishRequestDTO discountDishReq) throws Exception {
        DiscountDIsh discountDIsh = new DiscountDIsh();
        BeanUtils.copyProperties(discountDishReq, discountDIsh);
        discountDIsh.setDish(new Dish());
        discountDIsh.setCreatedAt(new Date());
        discountDIsh.getDish().setId(discountDishReq.getDishId());
        // condition
        if(discountDishReq.getConditions()!=null && !discountDishReq.getConditions().isEmpty()){
            discountDIsh.setConditions(new ArrayList<>());
            discountDishReq.getConditions().forEach((c)->{
                ConditionDiscountDish conditionDiscountDish = new ConditionDiscountDish();
                BeanUtils.copyProperties(c, conditionDiscountDish);
                discountDIsh.getConditions().add(conditionDiscountDish);
            });
        }
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", discountDishService.save(discountDIsh))
        );
    }

    @GetMapping("/get_all_discount_dish")
    @RolesAllowed({"ROLE_ADMIN","ROLE_PRODUCT_UPDATE"})
    public ResponseEntity<ResponseDTO> getAllDiscountDish(Pageable pageable) {
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", discountDishService.getAllDiscountDish(pageable))
        );
    }
}