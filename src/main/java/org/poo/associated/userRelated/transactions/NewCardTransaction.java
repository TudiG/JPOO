package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public final class NewCardTransaction extends Transaction {
    @JsonProperty("description")
    private String description;
    @JsonProperty("card")
    private String card;
    @JsonProperty("cardHolder")
    private String cardHolder;
    @JsonProperty("account")
    private String account;

    public NewCardTransaction(final Integer timestamp,
                              final String card,
                              final String cardHolder,
                              final String account) {
        super(timestamp);
        this.description = "New card created";
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }
}
