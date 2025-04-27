package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import com.cueballdb.model.BaseEntity;
import com.cueballdb.model.RoomCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoomDTO extends BaseDTO {

    @NotBlank(message = "Title " + Constants.RA_EMPTY_MESSAGE)
    private String title;

    @NotBlank(message = "Name " + Constants.RA_EMPTY_MESSAGE)
    private String name;

    @Min(value = 0, message = "Charges must be zero or positive")
    private double charges;

    private String roomCategoryName;

    @Size(min = 1, message = "At least one Game must be provided")
    private List<Integer> gameIds;

    @Min(value = 1, message = "At least one Category must be provided")
    private Integer roomCategoryId;

}
