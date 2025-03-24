package com.cueball.portal.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class VariantDTO extends BaseDTO {

    private String name;

    private boolean delete;

}
