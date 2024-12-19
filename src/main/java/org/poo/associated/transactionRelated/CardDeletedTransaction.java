package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

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

    public CardDeletedTransaction(final TransactionData transactionData) {
        super(transactionData.getTimestamp());
        this.description = "The card has been destroyed";
        this.card = transactionData.getCardNumber();
        this.cardHolder = transactionData.getEmail();
        this.account = transactionData.getAccount();
    }
}
