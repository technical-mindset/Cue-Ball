package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LocalizationDTO extends BaseDTO {
    @Size(min = Constants.RA_4, max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String section;

    @Size (max = Constants.RA_500)
    private String key;

    @Size(max = Constants.RA_255, message=Constants.RA_LENGTH_STRING)
    private String localText;

    @Size(max = Constants.RA_255, message=Constants.RA_LENGTH_STRING)
    private String pageType;

    @NotEmpty (message = "Language Code "+ Constants.RA_EMPTY_MESSAGE)
    private String langCode;
}
