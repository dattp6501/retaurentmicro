package com.dattp.order.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDTO {
    private Long id;
    private Long userId;
    private List<TableInCartRequestDTO> tables;
    private List<DishInCartRequestDTO> dishs;
    public CartResponseDTO() {
    }
}