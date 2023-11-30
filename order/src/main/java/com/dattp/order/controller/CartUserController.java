package com.dattp.order.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.dto.TableInCartRequestDTO;
import com.dattp.order.entity.TableInCart;
import com.dattp.order.service.CartService;

@RestController
@RequestMapping("/api/order/user/cart")
public class CartUserController {
    @Autowired
    private CartService cartService;

    @PostMapping
    @RequestMapping("/add_table_to_cart")
    public ResponseEntity<ResponseDTO> addTableToCart(@RequestBody @Valid TableInCartRequestDTO req){
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        TableInCart tableInCart = new TableInCart();
        BeanUtils.copyProperties(req, tableInCart);
        cartService.addTable(id, tableInCart);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }

    @GetMapping
    @RequestMapping("/get_table_in_cart")
    public ResponseEntity<ResponseDTO> getTableInCart(){
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                cartService.getTableInCart(id)
            )
        );
    }

    @PostMapping
    @RequestMapping("/delete_table_in_cart")
    public ResponseEntity<ResponseDTO> deleteTableInCart(@RequestBody @Valid TableInCartRequestDTO req){
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        cartService.deleteTableInCart(id, req.getTableId());
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }
}
