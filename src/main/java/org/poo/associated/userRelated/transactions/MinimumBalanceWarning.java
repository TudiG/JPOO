package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public final class MinimumBalanceWarning extends Transaction {
    @JsonProperty("description")
    private String description;

    public MinimumBalanceWarning(final Integer timestamp) {
        super(timestamp);
        description = "You have reached the minimum amount of funds, the card will be frozen";
    }
}
