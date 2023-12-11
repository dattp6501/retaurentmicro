package com.dattp.paymentservice.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentResponeDTO {
    private int code;//code return
    private long BookingId;// order id
    private String date;// date payment
    private float amount;// moner
    private String message;
    private String bankCode;// bank code
    private String tranCode;
    private String bankTranCode;// back transaction code
}