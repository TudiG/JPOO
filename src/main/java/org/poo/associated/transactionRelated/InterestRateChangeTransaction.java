package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Aceasta clasa este instantiata atunci cand se efectueaza schimbarea dobanzii
 * unui cont de tip SavingsAccount.
 */
public class InterestRateChangeTransaction extends Transaction {
    @JsonProperty("description")
    private String description;

    public InterestRateChangeTransaction(final Integer timestamp,
                                         final double interestRate) {
        super(timestamp);
        description = "Interest rate of the account changed to " + interestRate;
    }
}
