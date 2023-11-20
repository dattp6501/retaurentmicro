package com.dattp.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOOKED_DISH")
@Setter
@Getter
public class BookedDish {
    @Column(name = "state")
    private int state;
    
    @Column(name = "id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="dish_id")
    private long dishId;

    @Column(name="total")
    private int total;

    @Column(name="price")
    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_table_id")
    @JsonIgnore
    private BookedTable table;

    public BookedDish(){}

    @Override
    public boolean equals(Object obj) {
        BookedDish other = (BookedDish) obj;
        return this.id == other.id;
    }

}
