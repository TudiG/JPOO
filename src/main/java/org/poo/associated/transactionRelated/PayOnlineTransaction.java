package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Aceasta clasa este instantiata cand se efectueaza o plata cu cardul cu succes.
 */
public final class PayOnlineTransaction extends Transaction {
    @JsonProperty("description")
    private String description;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("commerciant")
    private String commerciant;

    public PayOnlineTransaction(final Integer timestamp,
                                final double amount,
                                final String commerciant) {
        super(timestamp);
        this.description = "Card payment";
        this.amount = amount;
        this.commerciant = commerciant;
    }
}
