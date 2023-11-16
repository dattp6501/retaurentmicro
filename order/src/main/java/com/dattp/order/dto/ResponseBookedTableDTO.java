package com.dattp.order.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBookedTableDTO {
    private int state;
    private long id;
    private long tableId;
    private float price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date from;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date to;
    private List<ResponseBookedDishDTO> dishs;
    public ResponseBookedTableDTO(long id, long tableId, float price, Date from, Date to, List<ResponseBookedDishDTO> dishs) {
        this.id = id;
        this.tableId = tableId;
        this.price = price;
        this.from = from;
        this.to = to;
        this.dishs = dishs;
    }
    public ResponseBookedTableDTO() {
    }
}
