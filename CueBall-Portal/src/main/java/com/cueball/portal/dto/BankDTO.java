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
public class BankDTO extends BaseDTO {

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Size(max = 500, message = Constants.RA_LENGTH_STRING_500)
    private String title;

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Size(max = 100, message = Constants.RA_LENGTH_STRING_100)
    private String name;

    @Size(min = 1, message = "Currency " + Constants.RA_EMPTY_MESSAGE)
    private List<Integer> currencyId;

    @PositiveOrZero(message = "must be positive or zero")
    @Max(value = 29, message = "Charges must be less than 30")
    @NotNull(message = Constants.RA_EMPTY_MESSAGE)
    private Double charges;

    @PositiveOrZero(message = "must be positive or zero")
    @Max(value = 29, message = "Charges must be less than 30")
    @NotNull(message = Constants.RA_EMPTY_MESSAGE)
    private Double additionalCharges;

    @Pattern(regexp = Constants.RA_IMAGE_REGEX_URL, message=Constants.RA_IMAGE_LENGTH_STRING_URL)
    @Size(max = Constants.RA_500, message=Constants.RA_LENGTH_STRING_MAX)
    private String imageUrl;

    @NotBlank(message = "Vendors " + Constants.RA_EMPTY_MESSAGE)
    private String vendors;

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    private String formulaType;

    @NotNull(message = Constants.RA_EMPTY_MESSAGE)
    private boolean exchangable;


}


