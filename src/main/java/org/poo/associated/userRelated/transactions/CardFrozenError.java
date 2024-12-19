package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public final class CardFrozenError extends Transaction {
    @JsonProperty("description")
    private String description;

    public CardFrozenError(final Integer timestamp) {
        super(timestamp);
        description = "The card is frozen";
    }
}
