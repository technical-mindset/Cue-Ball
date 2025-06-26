package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
public class BookingDTO extends CustomerDTO {

    @NotBlank(message = "Title " + Constants.RA_EMPTY_MESSAGE)
    @Size(max = 500, message = Constants.RA_LENGTH_STRING_500)
    private String title;

    @Min(value = 1, message = "Room " + Constants.RA_EMPTY_MESSAGE)
    private Integer roomId;

    // for searching in the list
    private String roomName;

    // for searching in the list
    private int customerId;

    private String checkOut;

    private String checkIn;

    private boolean cancel;

    private double charges;

    private boolean delete;

    @NotBlank(message = "Time-In " + Constants.RA_EMPTY_MESSAGE)
    private String timeIn;

    @NotBlank(message = "Time-Out " + Constants.RA_EMPTY_MESSAGE)
    private String timeOut;

    /** For Customer Check-Out */
    private double totalCharges;

    private String totalTime;

    private Integer roomCategoryId;

    private String roomCategoryName;

}


