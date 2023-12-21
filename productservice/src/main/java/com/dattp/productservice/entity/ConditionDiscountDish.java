package com.dattp.productservice.entity;

import java.util.stream.Stream;

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
@Table(name = "CONDITION_DISCOUNT_DISH")
@Getter
@Setter
public class ConditionDiscountDish {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name="value")
    private float value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    @JsonIgnore
    private DiscountDIsh discount;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    public void setTypeEnum(ConditionType type) {
        this.type = type.value();
    }
    public ConditionType getTypeEnum() {
        return Stream.of(ConditionType.values())
        .filter(t->t.value().equals(type))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
    }
}