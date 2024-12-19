package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import java.util.List;

/**
 * Aceasta clasa este instantiata cand este efectuata plata distribuita cu succes,
 * urmand sa fie adaugat la lista tranzactiile tuturor care au platit.
 */
public class SplitPaymentTransaction extends Transaction {
    @JsonProperty("description")
    protected String description;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("involvedAccounts")
    private List<String> involvedAccounts;

    public SplitPaymentTransaction(final Integer timestamp,
                                   final String description,
                                   final String currency,
                                   final double amount,
                                   final List<String> involvedAccounts) {
        super(timestamp);
        this.description = description;
        this.currency = currency;
        this.amount = amount;
        this.involvedAccounts = involvedAccounts;
    }
}
