package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Aceasta clasa se instantiaza cand se constanta ca unul dintre conturi
 * care participa la plata distribuita nu are fondurile necesare pentru a-si
 * plati partea. De asemenea, extinde clasa care semnaleaza succesul unei plati
 * distribuite pentru ca seamana foarte mult structural.
 */
public final class SplitPaymentError extends SplitPaymentTransaction {
    @JsonProperty("error")
    private String error;

    public SplitPaymentError(final Integer timestamp,
                             final String description,
                             final String currency,
                             final double amount,
                             final List<String> involvedAccounts,
                             final String failedAccount) {
        super(timestamp, description, currency, amount, involvedAccounts);
        this.error = "Account " + failedAccount + " has insufficient funds for a split payment.";
    }
}
