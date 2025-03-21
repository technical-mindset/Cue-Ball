package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class OperatingUnitDTO extends BaseDTO {

    @NotBlank(message = "Title " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String title;
//    private String clientId;
//    private String secretKey;
//    private String oauhtResourceIds;

    @NotBlank(message = "Title " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String username;
//    private String password;

    @Min(value = 1, message = "Legal Entity " +  Constants.RA_EMPTY_MESSAGE)
    private int legalEntityId;

    @Size(min = 1, message = "Currency " + Constants.RA_EMPTY_MESSAGE)
    private List<Integer> currenciesIds;

    @NotBlank(message = "Default checked currency " + Constants.RA_EMPTY_MESSAGE)
    private String defaultCurrency;

    @Size(min = 1, message = "Bank " + Constants.RA_EMPTY_MESSAGE)
    private List<Integer> bankIds;

    @Min(value = 1, message = "Default checked bank " + Constants.RA_EMPTY_MESSAGE)
    private Integer defaultBank;

    @NotBlank(message = "Order Prefix " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_10, message = Constants.RA_LENGTH_STRING_10)
    private String orderPrefix;

    private String legalEntityTitle;

}
