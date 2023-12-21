package com.dattp.productservice.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DISCOUNT_DISH")
@Getter
@Setter
public class DiscountDIsh {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private float value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dish_id")
    @JsonIgnore
    private Dish dish;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ConditionDiscountDish> conditions;

    @Column(name = "created_at")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date createdAt;

    @Column(name = "from_")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date from;

    @Column(name = "to_")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
}
