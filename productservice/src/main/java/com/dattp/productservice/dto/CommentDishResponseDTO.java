package com.dattp.productservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDishResponseDTO {
    private Long id;
    private int star;
    private String comment;
    private Long userId;
    private String username;
    public CommentDishResponseDTO() {
    }
}
