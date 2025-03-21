package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
public class LegalEntityDTO extends BaseDTO {

    @NotBlank(message = "Title " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String title;

    @NotBlank(message = "Description " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4, max = Constants.RA_500, message = Constants.RA_LENGTH_STRING_500)
    private String description;
}
