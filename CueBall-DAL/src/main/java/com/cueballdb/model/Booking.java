package com.cueballdb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


/**
 * This Entity hasn't any mapping but the LOGICAL mapping through id's.
 * */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking extends BaseEntity {

    @Column(name="title")
    private String title;

    @Column(name="room_id")
    private int roomId;

    @Column(name="customer_id")
    private int customerId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_in")
    private Date timeIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_out")
    private Date timeOut;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_out")
    private Date checkOut;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_in")
    private Date checkIn;

    @Column(name = "is_cancel")
    private boolean cancel;

    @Column(name = "delete")
    private boolean delete;

    private double charges;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;
}
