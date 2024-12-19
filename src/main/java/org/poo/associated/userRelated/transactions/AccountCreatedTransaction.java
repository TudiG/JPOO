package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public final class AccountCreatedTransaction extends Transaction {
    @JsonProperty("description")
    private String description;

    public AccountCreatedTransaction(final Integer timestamp) {
        super(timestamp);
        description = "New account created";
    }
}
