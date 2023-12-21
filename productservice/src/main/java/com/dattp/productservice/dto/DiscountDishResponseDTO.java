package com.dattp.productservice.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDishResponseDTO {
    private String code;
    private String type;
    private float value;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date from;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;
    private String description;
    public DiscountDishResponseDTO() {
    }
}
