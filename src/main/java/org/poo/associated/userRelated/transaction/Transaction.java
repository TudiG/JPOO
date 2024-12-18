package org.poo.associated.userRelated.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Transaction {
    protected Integer timestamp;

    public Transaction(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
