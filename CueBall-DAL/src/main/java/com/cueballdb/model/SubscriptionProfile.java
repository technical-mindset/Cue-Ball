package com.cueballdb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "subscription_profile")
public class SubscriptionProfile extends BaseEntity {


    //@Column(name="show_card")
    private String showCard;

    //@Column(name="frequency")
    private String frequency;

    //@Column(name="expire_date")
    private Date expiredDate;

    //@Column(name="type")
    private String type;

    //@Column(name="recurrence_id")
    private String recurrenceId;

    //@Column(name="customer_id")
    private String customerId;

    //@Column(name="bank_code")
    private String bankCode;

    //@Column(name = "donor_profile_id")
//    private DonorProfile donorProfile;

    //@Column(name = "operating_unit_id")
    private int operatingUnitId;

    //bi-directional many-to-one association to Transaction
    @OneToMany(mappedBy="subscriptionProfile")
    private List<Transaction> transactions = new ArrayList<>();
}
