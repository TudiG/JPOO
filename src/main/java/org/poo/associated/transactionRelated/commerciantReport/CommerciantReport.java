package org.poo.associated.transactionRelated.commerciantReport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

/**
 * Aceasta clasa este folosita pentru serializarea corecta a datelor cerute
 * de SpendingReport. Doar comenzile de tip PayOnline vor fi stocate intr-un
 * obiect de acest fel.
 */
@Getter
public final class CommerciantReport {
    private double total;
    private String commerciant;
    @JsonIgnore
    private Integer timestamp;
    @JsonIgnore
    private Transaction transaction;

    public CommerciantReport(final Integer timestamp,
                             final double total,
                             final String commerciant,
                             final Transaction transaction) {
        this.timestamp = timestamp;
        this.total = total;
        this.commerciant = commerciant;
        this.transaction = transaction;
    }
}
