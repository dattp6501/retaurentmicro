package com.dattp.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.order.dto.CartResponseDTO;
import com.dattp.order.dto.DishInCartRequestDTO;
import com.dattp.order.dto.TableInCartRequestDTO;
import com.dattp.order.entity.Cart;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.repository.CartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RedisService redisService;

    public Cart createCart(Cart cart){
        Cart cartSrc = cartRepository.findByUserId(cart.getUserId()).orElse(null);
        if(cartSrc != null) return cartSrc;
        return cartRepository.save(cart); 
    }

    public CartResponseDTO getById(Long userId){
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if(cart==null) return null;
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        BeanUtils.copyProperties(cart, cartResponseDTO);
        // table
        String keyTable = String.format("cart:%d:table", userId);
        List<Object> list = redisService.getAllElementFromListUseHash(keyTable);
        if(list!=null){
            cartResponseDTO.setTables(new ArrayList<>());
            list.forEach((e)->{
                cartResponseDTO.getTables().add((TableInCartRequestDTO)e);
            });
        }
        // dish
        String keyDish = String.format("cart:%d:dish", userId);
        list = redisService.getAllElementFromListUseHash(keyDish);
        if(list!=null){
            cartResponseDTO.setDishs(new ArrayList<>());
            list.forEach((e)->{
                cartResponseDTO.getDishs().add((DishInCartRequestDTO)e);
            });
        }
        return cartResponseDTO;
    }

    public boolean addTable(Long userId, TableInCartRequestDTO req) throws Exception{
        String keyTable = String.format("cart:%d:table", userId);
        if(redisService.getElementFromListUseHash(keyTable, req.getTableId()) != null) throw new BadRequestException("Bàn đã tồn tại trong danh sách");
        redisService.addElementToListUseHash(keyTable, req.getTableId(), req);
        return true;
    }

    public boolean deleteTableInCart(Long userId, TableInCartRequestDTO table){
        String keyTable = String.format("cart:%d:table", userId);
        return redisService.deleteElementFromListUseHash(keyTable, table.getTableId());
    }

    public boolean deleteAllTable(Long userId){
        String keyTable = String.format("cart:%d:table", userId);
        return redisService.delete(keyTable);
    }

    // dish
    public boolean addDishInCart(Long userId, DishInCartRequestDTO req) throws Exception{
        String keyDish = String.format("cart:%d:dish", userId);
        if(redisService.getElementFromListUseHash(keyDish, req.getDishId())!=null) throw new BadRequestException("Món ăn đã được thêm vào danh sách");
        return true;
    }

    public boolean deleteDishInCart(Long userId, DishInCartRequestDTO req){
        String keyDish = String.format("cart:%d:dish", userId);
        return redisService.deleteElementFromListUseHash(keyDish, req.getDishId());
    }

    public boolean deleteAllDish(Long userId){
        String keyDish = String.format("cart:%d:dish", userId);
        return redisService.delete(keyDish);
    }
}