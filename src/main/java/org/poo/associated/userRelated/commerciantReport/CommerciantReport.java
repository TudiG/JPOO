package org.poo.associated.userRelated.commerciantReport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

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
