package com.cueballdb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private String roomId;

    @Column(name="customer_id")
    private String customerId;

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

    @Column(name = "cancel")
    private boolean cancel;

    private double charges;

}
