package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewCardTransaction extends Transaction {
    @JsonProperty("description")
    private String description;
    @JsonProperty("card")
    private String card;
    @JsonProperty("cardHolder")
    private String cardHolder;
    @JsonProperty("account")
    private String account;

    public NewCardTransaction(Integer timestamp, String card, String cardHolder, String account) {
        super(timestamp);
        this.description = "New card created";
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }
}
