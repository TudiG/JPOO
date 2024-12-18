package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDeletedError extends Transaction {
    @JsonProperty("error")
    private String error;

    public AccountDeletedError(Integer timestamp) {
        super(timestamp);
        error = "Account couldn't be deleted - see org.poo.transactions for details";
    }
}
