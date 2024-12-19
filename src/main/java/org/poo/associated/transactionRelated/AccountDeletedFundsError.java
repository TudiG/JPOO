package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

/**
 * Aceasta clasa este instantiata cand se incearca stergerea unui cont
 * care inca are fonduri in el.
 */
public final class AccountDeletedFundsError extends Transaction {
    @JsonProperty("description")
    private String description;

    public AccountDeletedFundsError(final TransactionData transactionData) {
        super(transactionData.getTimestamp());
        description = "Account couldn't be deleted - there are funds remaining";
    }
}
