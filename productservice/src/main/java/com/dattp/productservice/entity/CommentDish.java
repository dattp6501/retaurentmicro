package com.dattp.productservice.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="COMMENT_DISH")
@Getter
@Setter
public class CommentDish {
    @Column(name="id") @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="star", nullable=false)
    private int star;

    @Column(name="comment")
    private String comment;

    @Column(name = "date_")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date date;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="id", column=@Column(name="user_id")),
        @AttributeOverride(name="username", column=@Column(name="username"))
    })
    private User user;

    @ManyToOne
    @JoinColumn(name="dish_id")
    private Dish dish;
}