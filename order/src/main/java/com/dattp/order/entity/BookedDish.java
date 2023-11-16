package com.dattp.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    private long dishID;

    @Column(name="total")
    private int total;

    @Column(name="price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "booked_table_id")
    @JsonIgnore
    private BookedTable table;

    public BookedDish(){}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (dishID ^ (dishID >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BookedDish))
            return false;
        BookedDish other = (BookedDish) obj;
        if (dishID != other.dishID)
            return false;
        return true;
    }

}
