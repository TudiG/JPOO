package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

/**
 * Aceasta clasa se instantiaza atunci cand se incearca plata cu un card
 * inghetat.
 */
public final class CardFrozenError extends Transaction {
    @JsonProperty("description")
    private String description;

    public CardFrozenError(final TransactionData transactionData) {
        super(transactionData.getTimestamp());
        description = "The card is frozen";
    }
}
