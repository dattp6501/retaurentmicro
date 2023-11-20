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
@Table(name = "TABLE_")
@Getter
@Setter
public class TableE {
    @Column(name = "state")
    private int state;

    @Column(name = "id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "amount_of_people", nullable=false)
    private int amountOfPeople;

    @Column(name = "price")
    private float price;

    @Column(name = "description")
    private String desciption;


    public TableE(){}
    public TableE(long id, String name, int amountOfPeople, float price, String desciption) {
        this.id = id;
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.price = price;
        this.desciption = desciption;
    }
}