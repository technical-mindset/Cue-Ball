package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
public class CustomerDTO extends BaseDTO {

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Size(max = 100, message = Constants.RA_LENGTH_STRING_100)
    private String customerName;

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Size(max = 100, message = Constants.RA_LENGTH_STRING_100)
    private String email;

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Size(max = 100, message = Constants.RA_LENGTH_STRING_100)
    private String contact;

}


