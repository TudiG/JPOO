package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Aceasta clasa este instantiata cand se incearca stergerea unui cont
 * care inca are fonduri in el.
 */
public final class AccountDeletedFundsError extends Transaction {
    @JsonProperty("description")
    private String description;

    public AccountDeletedFundsError(final Integer timestamp) {
        super(timestamp);
        description = "Account couldn't be deleted - there are funds remaining";
    }
}
