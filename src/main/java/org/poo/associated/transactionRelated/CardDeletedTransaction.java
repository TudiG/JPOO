package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Aceasta clasa este instantiata cand un card este
 * sters de un utilizator si atunci cand un card de tip
 * OneTime este folosit.
 */
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
