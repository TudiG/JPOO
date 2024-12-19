package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Aceasta clasa este instantiata cand se creeaza un cont nou pentru un user.
 */
public final class AccountCreatedTransaction extends Transaction {
    @JsonProperty("description")
    private String description;

    public AccountCreatedTransaction(final Integer timestamp) {
        super(timestamp);
        description = "New account created";
    }
}
