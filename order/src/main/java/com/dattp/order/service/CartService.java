package com.dattp.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dattp.order.entity.Cart;
import com.dattp.order.entity.TableInCart;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.repository.CartRepository;
import com.dattp.order.repository.TableInCartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TableInCartRepository tableInCartRepository;

    public Cart createCart(Cart cart){
        return cartRepository.save(cart); 
    }

    @Transactional
    public void addTable(Long cartId, TableInCart tableInCart){
        List<Object[]> list = tableInCartRepository.findByTableIdAndCartId(cartId, tableInCart.getTableId());
        if(!list.isEmpty()) 
            throw new BadRequestException("Bàn đã tồn tại trong danh sách");
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            cart = cartRepository.findByUserId(cartId).orElseThrow();
        }
        tableInCart.setCart(cart);
        tableInCartRepository.save(tableInCart);
    }

    public List<TableInCart> getTableInCart(Long cartId){
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null) cart = cartRepository.findByUserId(cartId).orElse(null);
        return cart.getTables();
    }

    @Transactional
    public int deleteTableInCart(Long cartId, Long tableId){
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null) cart = cartRepository.findByUserId(cartId).orElse(null);
        return tableInCartRepository.deleteByTableIdAndCartId(cart.getId(), tableId);
    }

    @Transactional
    public void deleteAllTable(Long cartId){
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null) cart = cartRepository.findByUserId(cartId).orElse(null);
        tableInCartRepository.deleteAllTableByCartId(cart.getId());
    }
}