package org.poo.associated.userRelated.commerciantReport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.poo.associated.userRelated.transaction.Transaction;

@Getter
public class CommerciantReport {
    private double total;
    private String commerciant;
    @JsonIgnore
    private Integer timestamp;
    @JsonIgnore
    Transaction transaction;

    public CommerciantReport(Integer timestamp, double total, String commerciant, Transaction transaction) {
        this.timestamp = timestamp;
        this.total = total;
        this.commerciant = commerciant;
        this.transaction = transaction;
    }
}
