package com.cueballdb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory extends BaseEntity {

    private String name;

    @Column(name = "variant_id")
    private Integer variantId;

    @ManyToOne
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    private Variant variant;

    private int quantity;

    private double price;

    @ManyToOne
    private InventoryCategory inventoryCategory;

}
