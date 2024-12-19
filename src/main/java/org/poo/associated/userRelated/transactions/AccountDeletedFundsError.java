package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public final class AccountDeletedFundsError extends Transaction {
    @JsonProperty("description")
    private String description;

    public AccountDeletedFundsError(final Integer timestamp) {
        super(timestamp);
        description = "Account couldn't be deleted - there are funds remaining";
    }
}
