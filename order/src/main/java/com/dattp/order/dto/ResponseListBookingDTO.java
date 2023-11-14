package com.dattp.order.dto;

import java.util.List;

import com.dattp.order.entity.Booking;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class ResponseListBookingDTO {
    private int code;
    private String message;
    private List<Booking> bookings;
}