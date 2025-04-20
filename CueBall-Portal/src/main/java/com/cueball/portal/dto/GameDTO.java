package com.cueball.portal.dto;


import com.cueball.portal.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO extends BaseDTO {

    @NotBlank(message = "Title " + Constants.RA_EMPTY_MESSAGE)
    private String title;

    @NotNull(message = "Charges " + Constants.RA_EMPTY_MESSAGE)
    @Min(value = 1, message = "Charges must be at least 1")
    private Double charges;

    @NotNull(message = "Players " + Constants.RA_EMPTY_MESSAGE)
    @Min(value = 1, message = "Players must be at least 1")
    private Integer maxPlayers;

}
