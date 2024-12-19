package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Aceasta clasa este instantiata atunci cand se da comanda CheckBalance si se
 * constanta faptul ca s-a trecut sub balanta minima, nu cand se face o plata,
 * trecandu-se sub balanta minima a unui cont.
 */
public final class MinimumBalanceWarning extends Transaction {
    @JsonProperty("description")
    private String description;

    public MinimumBalanceWarning(final Integer timestamp) {
        super(timestamp);
        description = "You have reached the minimum amount of funds, the card will be frozen";
    }
}
