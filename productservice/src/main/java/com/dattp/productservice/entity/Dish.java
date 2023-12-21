package com.dattp.productservice.entity;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="DISH")
@Getter
@Setter
public class Dish {
    @Column(name="state")
    private int state;

    @Column(name = "id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name="image")
    private byte[] image;

    @Column(name="description")
    private String description;
    
    @OneToMany(mappedBy="dish", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentDish> CommentDishs;

    @OneToMany(mappedBy="dish", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiscountDIsh> discounts;

    public Dish() {
    }

    public DiscountDIsh getDiscountActive(){
        if(this.discounts==null || this.discounts.isEmpty()) return null;
        // There is only 1 discount per period
        final Date currentDate = new Date();
        for(DiscountDIsh dc : this.discounts){
            if(0>=dc.getFrom().compareTo(currentDate)&&currentDate.compareTo(dc.getTo())<=0){
                return dc;
            }
        }
        return null;
    }
    
    public float getAmountDiscount(){
        DiscountDIsh dc = getDiscountActive();
        if(dc==null) return 0;
        if(dc.getType().equals("%")) return this.price*dc.getValue()/100; 
        return dc.getValue();
    }
}