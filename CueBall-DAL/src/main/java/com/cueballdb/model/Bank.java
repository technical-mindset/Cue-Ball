package com.cueballdb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank")
public class Bank extends BaseEntity {

    private double charges;

    //@Column(name="additional_charges")
    private double additionalCharges;

    //@Column(name="title")
    private String title;

    private String name;

    //@Column(name="currency_id")
    private String currencyId;

    //@Column(name="vendors")
    private String vendors;

    //@Column(name="image_url")
    private String imageUrl;

    //@Column(name="formula_type")
    private String formulaType;

    //@Column(name="exchangable")
    private boolean exchangable;



}
