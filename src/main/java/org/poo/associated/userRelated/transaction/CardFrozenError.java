package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardFrozenError extends Transaction {
    @JsonProperty("description")
    private String description;

    public CardFrozenError(Integer timestamp) {
        super(timestamp);
        description = "The card is frozen";
    }
}
