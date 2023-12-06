package com.dattp.paymentservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOOKING")
@Getter
@Setter
public class Booking {
    @Id
    private long id;

    private int state;

    @Column(name = "custemer_id")
    private long CustomerId;

    private float deposits;

    private String email;

    @Column(name = "custemer_fullname")
    private String custemerFullname;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date date;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    @Column(name = "from_")
    private Date from;

    @Column(name = "to_")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;
    
    private String description;
    public Booking(){super();}
}
