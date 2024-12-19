package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public final class InsufficientFundsError extends Transaction {
    @JsonProperty("description")
    private String description;

    public InsufficientFundsError(final Integer timestamp) {
        super(timestamp);
        description = "Insufficient funds";
    }
}
