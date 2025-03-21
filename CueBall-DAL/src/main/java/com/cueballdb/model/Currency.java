package com.cueballdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
//@Entity
//@Table(name = "currency")
public class Currency extends BaseEntity{

    //@Column(name = "name")
    private String name;

    //@Column(name="currency_code")
    private String currencyCode;

    //@Column(name="exchange_PKR")
    private double exchangePkr;

    //@Column(name = "expire_date")
    private long expireDate;

    // bi-directional many-to-many association to OperatingUnit
    @ManyToMany(mappedBy = "currencies")
    private List<OperatingUnit> operatingUnits;

}
