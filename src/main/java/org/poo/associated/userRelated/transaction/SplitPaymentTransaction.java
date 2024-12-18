package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SplitPaymentTransaction extends Transaction {
    @JsonProperty("description")
    protected String description;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("involvedAccounts")
    private List<String> involvedAccounts;

    public SplitPaymentTransaction(Integer timestamp, String description, String currency, double amount, List<String> involvedAccounts) {
        super(timestamp);
        this.description = description;
        this.currency = currency;
        this.amount = amount;
        this.involvedAccounts = involvedAccounts;
    }
}
