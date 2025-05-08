package com.cueballdb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * This Entity hasn't any mapping but the LOGICAL mapping through id's.
 * */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task extends BaseEntity {

    @Column(name="description")
    private String description;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="shift")
    private String shift;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "task_date")
    private Date taskDate;

    @Column(name = "last_alert_sent")
    private LocalDateTime lastAlertSent;

    @Column(name = "complete")
    private boolean complete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

}
