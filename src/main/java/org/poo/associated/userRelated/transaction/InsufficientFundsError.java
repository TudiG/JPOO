package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsufficientFundsError extends Transaction {
    @JsonProperty("description")
    private String description;

    public InsufficientFundsError(Integer timestamp) {
        super(timestamp);
        description = "Insufficient funds";
    }
}
