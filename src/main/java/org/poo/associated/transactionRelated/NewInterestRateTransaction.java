package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

/**
 * Aceasta clasa este instantiata atunci cand se efectueaza schimbarea dobanzii
 * unui cont de tip SavingsAccount.
 */
public final class NewInterestRateTransaction extends Transaction {
    @JsonProperty("description")
    private String description;

    public NewInterestRateTransaction(final TransactionData transactionData) {
        super(transactionData.getTimestamp());
        description = "Interest rate of the account changed to "
                + transactionData.getInterestRate();
    }
}
