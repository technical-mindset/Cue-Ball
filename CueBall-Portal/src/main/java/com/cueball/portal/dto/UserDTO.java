package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Data
public class UserDTO extends BaseDTO{

    @NotBlank(message = "User Name " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String username;

    @NotBlank(message = "Full Name " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String fullname;

    @NotBlank(message = "Password " + Constants.RA_EMPTY_MESSAGE)
    @Size(min = Constants.RA_4,max = Constants.RA_255, message = Constants.RA_LENGTH_STRING)
    private String password;


    @NotEmpty(message = "Roles " + Constants.RA_EMPTY_MESSAGE)
    private List<Integer> roleId;

}
