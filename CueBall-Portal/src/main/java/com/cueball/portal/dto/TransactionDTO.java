package com.cueball.portal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TransactionDTO extends BaseDTO {
    private int emailSent;
    private String successIndicator;
    private int bankId;
    private int campaignId;
    private int operatingUnitId;
    private int legalEntityId;
    private boolean advertisement;
    private double actualAmount;
    private double convertedAmount;
    private double withTaxAmount;
    private String type;
    private String orderId;
    private String isoCurrencyCode;
    private String countryCode;
    private String exchangeRate;
    private String bankTransactionId;
    private String bankTransactionStatus;
    private String bankDescription;
    private String bankResponseCode;
    private String marketingSource;
    private String name;
    private String email;
    private String contact;
    private String bankName;
    private String campaignTitle;
    private String operatingUnitTitle;
}
