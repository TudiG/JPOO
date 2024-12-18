package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayOnlineTransaction extends Transaction {
    @JsonProperty("description")
    private String description;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("commerciant")
    private String commerciant;

    public PayOnlineTransaction(Integer timestamp, double amount, String commerciant) {
        super(timestamp);
        this.description = "Card payment";
        this.amount = amount;
        this.commerciant = commerciant;
    }
}
