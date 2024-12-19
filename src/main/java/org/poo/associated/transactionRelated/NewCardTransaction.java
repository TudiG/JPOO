package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Aceasta clasa este instantiata atunci cand este adaugat un card unui cont,
 * indiferent de ce tip de card este, dar doat atunci cand user-ul creeaza un
 * card pentru contul sau. De asemenea este instantiata atunci cand un card
 * de tip OneTime efectueaza o plata cu succes.
 */
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
