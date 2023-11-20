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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="BOOKED_TABLE")
@Getter
@Setter
@AllArgsConstructor
public class BookedTable {
    @Column(name = "state")
    private int state;
    
    @Column(name="id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name="table_id", nullable=false)
    private long tableId;
    
    @Column(name="price", nullable=false)
    private float price;
    
    @Column(name="from_", nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date from;

    @Column(name="to_", nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date to;

    @OneToMany(mappedBy="table", cascade={CascadeType.ALL}, orphanRemoval = true)
    private List<BookedDish> dishs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="booking_id")
    private Booking booking;
    
    public BookedTable(){}

    @Override
    public boolean equals(Object obj) {
        BookedTable other = (BookedTable) obj;
        return this.tableId == other.tableId;
    }
}