package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Mentionez ca aceasta clasa este instantiata pentru PayOnline si SendMoney
 * atunci cand un cont are fonduri insuficiente.
 */
public final class InsufficientFundsError extends Transaction {
    @JsonProperty("description")
    private String description;

    public InsufficientFundsError(final Integer timestamp) {
        super(timestamp);
        description = "Insufficient funds";
    }
}
