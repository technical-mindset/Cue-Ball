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
@Table(name = "tuc_buying")
public class TucBuying extends BaseEntity {

    private String customerId;

    private String inventoryId;

    private String inventoryName;

    private int quantity;

    private double price;
}
