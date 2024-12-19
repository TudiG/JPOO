package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public class InterestRateChangeTransaction extends Transaction {
    @JsonProperty("description")
    private String description;

    public InterestRateChangeTransaction(final Integer timestamp,
                                         final double interestRate) {
        super(timestamp);
        description = "Interest rate of the account changed to " + interestRate;
    }
}
