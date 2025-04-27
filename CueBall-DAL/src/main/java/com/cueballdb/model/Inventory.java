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

    @Column(name = "restaurant_id")
    private int restaurantId;

    private int quantity;

    private double price;

    @Column(name = "actual_price")
    private double actualPrice;

    @ManyToOne
    private InventoryCategory inventoryCategory;

}
