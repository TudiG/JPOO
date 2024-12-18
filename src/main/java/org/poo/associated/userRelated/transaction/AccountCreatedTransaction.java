package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCreatedTransaction extends Transaction {
    @JsonProperty("description")
    private String description;

    public AccountCreatedTransaction(Integer timestamp) {
        super(timestamp);
        description = "New account created";
    }
}
