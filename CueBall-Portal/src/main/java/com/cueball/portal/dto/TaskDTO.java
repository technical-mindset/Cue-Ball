package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@NoArgsConstructor
@Setter
@Getter
public class TaskDTO extends BaseDTO {

    @NotBlank(message = "Description " + Constants.RA_EMPTY_MESSAGE)
    @Size(max = 1000, message = Constants.RA_LENGTH_STRING_500)
    private String description;

    @Min(value = 1, message = "User " + Constants.RA_EMPTY_MESSAGE)
    private Integer userId;

    // for searching in the list
    private String userName;

    @Size(min = 3, message = "Shift " + Constants.RA_EDIT_MESSAGE)
    private String shift;

    @NotBlank(message = "Task-Date " + Constants.RA_EMPTY_MESSAGE)
    private String taskDate;

    private String lastAlertSent;

    private String roleName;

    private boolean complete;
}


