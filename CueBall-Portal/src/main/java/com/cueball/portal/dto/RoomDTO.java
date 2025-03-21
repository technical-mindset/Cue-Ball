package com.cueball.portal.dto;

import com.cueballdb.model.BaseEntity;
import com.cueballdb.model.RoomCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoomDTO extends BaseDTO {

    private String title;

    private String name;

    private double charges;

    private String roomCategoryName;

    private List<Integer> gameIds;

    private Integer roomCategoryId;

}
