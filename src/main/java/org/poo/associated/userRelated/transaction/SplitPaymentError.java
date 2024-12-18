package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SplitPaymentError extends SplitPaymentTransaction {
    @JsonProperty("error")
    private String error;

    public SplitPaymentError(Integer timestamp, String description, String currency, double amount, List<String> involvedAccounts, String error) {
        super(timestamp, description, currency, amount, involvedAccounts);
        this.error = error;
    }
}
