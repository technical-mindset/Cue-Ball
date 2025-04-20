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
public class InventoryDTO extends BaseDTO {

    @NotBlank(message = "Name " + Constants.RA_EMPTY_MESSAGE)
    @Size(max = 100, message = Constants.RA_LENGTH_STRING_100)
    private String name;

    @Min(value = 1, message = "Variant " + Constants.RA_EMPTY_MESSAGE)
    private Integer variantId;

    @Min(value = 1, message = "Category " + Constants.RA_EMPTY_MESSAGE)
    private Integer inventoryCategoryId;

    @Min(value = 0, message = "Quantity must be zero or positive")
    private int quantity;

    @Min(value = 0, message = "Price must be zero or positive")
    private double price;

    // For searching and displaying in the table
    private String variantName;

    private String inventoryCategoryName;
}


