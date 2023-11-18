package com.dattp.order.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOOKING")
@Getter
@Setter
@AllArgsConstructor
public class Booking {
    @Column(name = "state")
    private int state;

    @Column(name="id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id")
    private long CustomerId;

    @Column(name = "date", nullable = false)
    // @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @Column(name = "desciption")
    private String description;

    @OneToMany(mappedBy = "booking", cascade ={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    private List<BookedTable> bookedTables;

    public Booking(){
        super();
    }
}
