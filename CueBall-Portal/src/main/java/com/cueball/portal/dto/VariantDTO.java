package com.cueball.portal.dto;


import com.cueball.portal.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class VariantDTO extends BaseDTO {

    @NotBlank(message = "Name " + Constants.RA_EMPTY_MESSAGE)
    private String name;

    private boolean delete;

}
