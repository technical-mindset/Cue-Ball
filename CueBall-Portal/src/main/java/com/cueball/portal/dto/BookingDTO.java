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

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Size(max = 500, message = Constants.RA_LENGTH_STRING_500)
    private String title;

    private String roomId;

    private String customerId;

    private String checkOut;

    private String checkIn;

    private boolean cancel;

    private double charges;

    private String time;

}


