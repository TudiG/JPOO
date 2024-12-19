package org.poo.associated.userRelated.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

public final class SendMoneyTransaction extends Transaction {
    @JsonProperty("description")
    private String description;
    @JsonProperty("senderIBAN")
    private String senderIBAN;
    @JsonProperty("receiverIBAN")
    private String receiverIBAN;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("transferType")
    private String transferType;

    public SendMoneyTransaction(final Integer timestamp,
                                final String description,
                                final String senderIBAN,
                                final String receiverIBAN,
                                final String amount,
                                final String transferType) {
        super(timestamp);
        this.description = description;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.transferType = transferType;
    }
}
