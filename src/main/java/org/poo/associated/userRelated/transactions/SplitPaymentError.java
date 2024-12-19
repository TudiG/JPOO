package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class SplitPaymentError extends SplitPaymentTransaction {
    @JsonProperty("error")
    private String error;

    public SplitPaymentError(final Integer timestamp,
                             final String description,
                             final String currency,
                             final double amount,
                             final List<String> involvedAccounts,
                             final String error) {
        super(timestamp, description, currency, amount, involvedAccounts);
        this.error = "Account " + error + " has insufficient funds for a split payment.";
    }
}
