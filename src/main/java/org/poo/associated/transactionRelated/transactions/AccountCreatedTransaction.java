package org.poo.associated.transactionRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

/**
 * Aceasta clasa este instantiata cand se creeaza un cont nou pentru un user.
 */
public final class AccountCreatedTransaction extends Transaction {
    @JsonProperty("description")
    private String description;

    public AccountCreatedTransaction(final TransactionData transactionData) {
        super(transactionData.getTimestamp());
        description = "New account created";
    }
}
