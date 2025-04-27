package com.cueballdb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
//@Entity
//@Table(name = "transaction")
public class Transaction extends BaseEntity {

    private String type;

    // --- --- Amount Columns --- ---
    //@Column(name = "actual_amount")
    private double actualAmount;

    private double convertedAmount;

    //@Column(name = "with_tax_amount")
    private double withTaxAmount;

    //@Column(name="iso_currency_code")
    private String isoCurrencyCode;

    //@Column(name="country_code")
    private String countryCode;

    //@Column(name="email_sent")
    private int emailSent;

    //@Column(name="exchange_rate")
    private String exchangeRate;
    // --- x --- x --- x ---

    // --- --- Banks Columns --- ---
    //@Column(name="bank_transaction_id")
    private String bankTransactionId;

    //@Column(name="bank_transaction_status")
    private String bankTransactionStatus;

    //@Column(name="bank_description")
    private String bankDescription;

    //@Column(name="bank_response_code")
    private String bankResponseCode;

    //@Column(name="success_indicator")
    private int successIndicator;
    // --- x --- x --- x ---

    // --- --- Order Columns --- ---
    //@Column(name="order_id")
    private String orderId;
    // --- x --- x --- x ---

    // --- --- Marketing Columns --- ---
    //@Column(name="marketing_source")
    private String marketingSource;

    //@Column(name="advertisment")
    private int advertisement;
    // --- x --- x --- x ---

    // --- --- FK Columns --- ---
    //@Column(name="bank_id")
    private int bankId;

    //@Column(name = "campaign_id")
    private int campaignId;

    //@Column(name = "operating_unit_id")
    private int operatingUnitId;

    //@Column(name = "legal_entity_id")
    private int legalEntityId;

    @ManyToOne
    @JoinColumn(name = "campaign_id", insertable = false, updatable = false)
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "operating_unit_id", insertable = false, updatable = false)
    private OperatingUnit operatingUnit;

    @ManyToOne
    @JoinColumn(name = "bank_id", insertable = false, updatable = false)  // Avoid duplicate inserts/updates
    private Bank bank;

    //bi-directional many-to-one association to SubscriptionProfile
//    @ManyToOne
//    private SubscriptionProfile subscriptionProfile;

//    @ManyToOne
//    private DonorProfile donorProfile;
}
