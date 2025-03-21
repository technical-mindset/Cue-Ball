package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@Setter
@Getter
public class CurrencyDTO extends BaseDTO {

//    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Size(max = Constants.RA_20, message = Constants.RA_LENGTH_STRING_20)
    private String name;

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Pattern(regexp = Constants.RA_REGEX_CURRENCY, message = "must be 3 uppercase letters")
    private String currencyCode;

//    @PositiveOrZero(message = "must be positive or zero")
//    @Max(value = 29, message = "Exchange rate must be less than 30")
//    @NotNull(message = Constants.RA_EMPTY_MESSAGE)
    private Double exchangePkr;

    private long expireDate;
}
