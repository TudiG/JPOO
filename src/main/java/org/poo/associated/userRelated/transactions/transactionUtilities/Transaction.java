package org.poo.associated.userRelated.transactions.transactionUtilities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Transaction {
    protected Integer timestamp;

    public Transaction(final Integer timestamp) {
        this.timestamp = timestamp;
    }
}
