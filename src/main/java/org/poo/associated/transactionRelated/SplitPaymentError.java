package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

/**
 * Aceasta clasa se instantiaza cand se constanta ca unul dintre conturi
 * care participa la plata distribuita nu are fondurile necesare pentru a-si
 * plati partea. De asemenea, extinde clasa care semnaleaza succesul unei plati
 * distribuite pentru ca seamana foarte mult structural.
 */
public final class SplitPaymentError extends SplitPaymentTransaction {
    @JsonProperty("error")
    private String error;

    public SplitPaymentError(final TransactionData transactionData) {
        super(transactionData);
        this.error = "Account " + transactionData.getAccount()
                + " has insufficient funds for a split payment.";
    }
}
