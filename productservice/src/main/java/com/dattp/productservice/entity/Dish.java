package com.dattp.productservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="DISH")
@Getter
@Setter
public class Dish {
    @Column(name = "id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name="description")
    private String discription;

    public Dish(long id, String name, float price, String discription) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discription = discription;
    }

    public Dish() {
    }
}