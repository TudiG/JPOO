package org.poo.associated.transactionRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

/**
 * Mentionez ca aceasta clasa este instantiata pentru PayOnline si SendMoney
 * atunci cand un cont are fonduri insuficiente.
 */
public final class InsufficientFundsError extends Transaction {
    @JsonProperty("description")
    private String description;

    public InsufficientFundsError(final TransactionData transactionData) {
        super(transactionData.getTimestamp());
        description = "Insufficient funds";
    }
}
