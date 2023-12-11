package com.dattp.paymentservice.utils.payment;

public enum PaymentType {
    VNPAY("VNPAY")
    ,DEFAULT("DEFAULT");
    private final String type;
    private PaymentType(String type){
        this.type = type;
    }

    public String value(){
        return this.type;
    }
}