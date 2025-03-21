package com.cueballdb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
public class Game extends BaseEntity{

    @Column(name="title")
    private String title;

    @Column(name = "price")
    private double charges;

    @Column(name = "max_players")
    private int maxPlayers;

}
