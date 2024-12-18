package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MinimumBalanceWarning extends Transaction {
    @JsonProperty("description")
    private String description;

    public MinimumBalanceWarning(Integer timestamp) {
        super(timestamp);
        description = "You have reached the minimum amount of funds, the card will be frozen";
    }
}
