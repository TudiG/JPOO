package org.poo.associated.transactionRelated.transactionUtilities;

import lombok.Getter;

/**
 * Interfata de baza pentru toate tranzactiile si erorile
 * disponibile. Campurile specifice fiecarei tranzactii se
 * regasesc in clasele lor.
 *
 * Pentru a ma asigura ca sunt serializate campurile, am folosit
 * @JsonProperty de foarte multe ori in toate subclasele lui
 * Transaction.
 */
@Getter
public abstract class Transaction {
    protected Integer timestamp;

    public Transaction(final Integer timestamp) {
        this.timestamp = timestamp;
    }
}
