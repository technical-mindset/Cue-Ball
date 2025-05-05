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
public class tuckBuyingDTO extends BaseDTO {

    private String customerId;

    private String inventoryId;

    private int quantity;

    private double price;

}


