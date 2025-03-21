package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Data;

import javax.validation.constraints.*;;



@Data
public class CampaignDTO extends BaseDTO {

    @NotBlank(message = "Title " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String title;

    @NotBlank(message = "Description " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4, max = Constants.RA_500, message = Constants.RA_LENGTH_STRING_500)
    private String description;

    //    @NotBlank(message = "Show Home " + Constants.RA_EMPTY_MESSAGE)
//    @Size(min = Constants.RA_4, max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private boolean showHome;

    @Pattern(regexp = Constants.RA_IMAGE_REGEX_URL, message=Constants.RA_IMAGE_LENGTH_STRING_URL)
    @Size(max = Constants.RA_500, message=Constants.RA_LENGTH_STRING_MAX)
    private String imageUrl;

    @Pattern(regexp = Constants.RA_IMAGE_REGEX_URL, message=Constants.RA_IMAGE_LENGTH_STRING_URL)
    @Size(max = Constants.RA_500, message=Constants.RA_LENGTH_STRING_MAX)
    private String iconUrl;

    @NotBlank(message = "Banner Title " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String bannerTitle;

    @NotBlank(message = "Description " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4, max = Constants.RA_500, message = Constants.RA_LENGTH_STRING_500)
    private String bannerDescription;

    private String romanUrl;

    private boolean sadqa;

    private boolean zakat;

    private String startDate;

    private String endDate;

    private boolean lifeTime;

    private boolean root;

    @Min(value = 1, message = Constants.RA_EMPTY_MESSAGE)
    private Integer operatingUnitId;
}
