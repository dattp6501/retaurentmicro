package com.dattp.paymentservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "HISTORY_PAYMENT")
@Getter
@Setter
public class HistoryPayment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "code")
    private int code;//code return

    @Column(name = "booking_id")
    private long BookingId;// order id

    @Column(name = "date")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private String date;// date payment

    @Column(name = "amount")
    private float amount;// moner

    @Column(name = "message")
    private String message;

    @Column(name = "bank_code")
    private String bankCode;// bank code

    @Column(name = "tran_code")
    private String tranCode;

    @Column(name = "bank_tran_code")
    private String bankTranCode;// back transaction code

    public HistoryPayment(){super();}
}
