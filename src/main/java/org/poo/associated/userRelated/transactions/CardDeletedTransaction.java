package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public final class CardDeletedTransaction extends Transaction {
    @JsonProperty("description")
    private String description;
    @JsonProperty("card")
    private String card;
    @JsonProperty("cardHolder")
    private String cardHolder;
    @JsonProperty("account")
    private String account;

    public CardDeletedTransaction(final Integer timestamp,
                                  final String card,
                                  final String cardHolder,
                                  final String account) {
        super(timestamp);
        this.description = "The card has been destroyed";
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }
}
