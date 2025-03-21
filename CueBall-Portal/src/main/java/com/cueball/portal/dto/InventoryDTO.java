package com.cueball.portal.dto;

import com.cueball.portal.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@NoArgsConstructor
@Setter
@Getter
public class InventoryDTO extends BaseDTO {

    @NotBlank(message = Constants.RA_EMPTY_MESSAGE)
    @Size(max = 100, message = Constants.RA_LENGTH_STRING_100)
    private String name;

    private String variant;

    private int quantity;

    private double price;

    private Integer inventoryCategoryId;

    private String inventoryCategoryName;
}


