package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardDeletedTransaction extends Transaction {
    @JsonProperty("description")
    private String description;
    @JsonProperty("card")
    private String card;
    @JsonProperty("cardHolder")
    private String cardHolder;
    @JsonProperty("account")
    private String account;

    public CardDeletedTransaction(Integer timestamp, String card, String cardHolder, String account) {
        super(timestamp);
        this.description = "The card has been destroyed";
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }
}
