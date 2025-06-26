package com.cueballdb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "room")
public class Room extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "charges")
    private double charges;

    @Column(name = "game_id")
    private String games;

    @Column(name = "delete")
    private boolean delete;

    // bi-directional many-to-one association to RoomCategory
    @ManyToOne
    @JoinColumn(name="room_category_id")
    private RoomCategory roomCategory;

}
