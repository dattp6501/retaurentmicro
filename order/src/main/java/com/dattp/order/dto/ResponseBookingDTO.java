package com.dattp.order.dto;

import com.dattp.order.entity.Booking;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class ResponseBookingDTO {
    private int code;
    private String message;
    private Booking booking;
}
