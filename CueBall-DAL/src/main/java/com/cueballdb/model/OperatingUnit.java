package com.cueballdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
//@Entity
//@Table(name = "operating_unit")
public class OperatingUnit extends BaseEntity {
    //@Column(name = "title")
    private String title;

    // --- for authorization server ---
    //@Column(name="client_id")
    private String clientId;

    //@Column(name="secret_key")
    private String secretKey;

    //@Column(name="resource_id")
    private String oauhtResourceIds;

    //@Column(name = "username")
    private String username;

    //@Column(name = "password")
    private String password;

    //@Column(name = "order_prefix")
    private String orderPrefix;

    // --- --- --- --- --- --- --- --- -

    // getting multiple bank's id by comma separated
    //@Column(name = "bank_id")
    private String bankIds;

    //@Column(name = "default_bank")
    private Integer defaultBank;

    //@Column(name = "default_currency")
    private String defaultCurrency;

    // bi-directional many-to-one association to RoomCategory
    @ManyToOne
    @JoinColumn(name="room_category_id")
    private RoomCategory roomCategory;

    @OneToMany(mappedBy = "operatingUnit")
    private List<Campaign> campaigns;

    // bi-directional many-to-many association to Currency
    @ManyToMany
    @JoinTable(name = "ou_currency_bridge",
            joinColumns = {@JoinColumn(name = "operating_unit_id")},
            inverseJoinColumns = {@JoinColumn(name = "currency_id")})
    private List<Currency> currencies = new ArrayList<>();


    public OperatingUnit(OperatingUnit operatingUnit){
        this.setId(operatingUnit.getId());
        this.username = operatingUnit.username;
        this.password = operatingUnit.password;
    }

}
