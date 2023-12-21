package com.dattp.order.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.dto.CartResponseDTO;
import com.dattp.order.dto.DishInCartRequestDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.dto.TableInCartRequestDTO;
import com.dattp.order.service.CartService;


@RestController
@RequestMapping("/api/order/user/cart")
public class CartUserController {
    @Autowired
    private CartService cartService;



    @GetMapping("/get_cart")
    public ResponseEntity<ResponseDTO> getCart() {
        Long id = Long.parseLong(
            SecurityContextHolder.getContext().getAuthentication().getName().split("///")[0]
        );
        CartResponseDTO cartResponseDTO = cartService.getById(id);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                cartResponseDTO
            )
        );
    }
    

    // table
    @PostMapping
    @RequestMapping("/add_table_to_cart")
    public ResponseEntity<ResponseDTO> addTableToCart(@RequestBody @Valid TableInCartRequestDTO req) throws Exception{
        Long id = Long.parseLong(
            SecurityContextHolder.getContext().getAuthentication().getName().split("///")[0]
        );
        cartService.addTable(id, req);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }

    @PostMapping
    @RequestMapping("/delete_table_in_cart")
    public ResponseEntity<ResponseDTO> deleteTableInCart(@RequestBody @Valid TableInCartRequestDTO req){
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName().split("///")[0]);
        cartService.deleteTableInCart(id, req);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }

    // dish
    @PostMapping
    @RequestMapping("/add_dish_to_cart")
    public ResponseEntity<ResponseDTO> addDishToCart(@RequestBody @Valid DishInCartRequestDTO req) throws Exception{
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName().split("///")[0]);
        cartService.addDishInCart(id, req);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }
}
