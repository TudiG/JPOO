package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

import java.util.List;

/**
 * Aceasta clasa este instantiata cand este efectuata plata distribuita cu succes,
 * urmand sa fie adaugat la lista tranzactiile tuturor care au platit.
 */
public class SplitPaymentTransaction extends Transaction {
    @JsonProperty("description")
    protected String description;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("involvedAccounts")
    private List<String> involvedAccounts;

    public SplitPaymentTransaction(final TransactionData transactionData) {
        super(transactionData.getTimestamp());
        this.description = transactionData.getDescription();
        this.currency = transactionData.getCurrency();
        this.amount = transactionData.getAmount();
        this.involvedAccounts = transactionData.getInvolvedAccounts();
    }
}
