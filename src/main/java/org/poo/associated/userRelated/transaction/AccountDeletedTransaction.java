package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDeletedTransaction extends Transaction {
    @JsonProperty("success")
    private String success;

    public AccountDeletedTransaction(Integer timestamp) {
        super(timestamp);
        success = "Account deleted";
    }
}
