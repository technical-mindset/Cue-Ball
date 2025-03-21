package com.cueballdb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "room_category")
public class RoomCategory extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "roomCategory")
    private List<Room> room;

}
