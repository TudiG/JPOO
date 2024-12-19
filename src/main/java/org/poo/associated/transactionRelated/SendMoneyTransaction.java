package org.poo.associated.transactionRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;

/**
 * Aceasta clasa se instantiaza atunci cand se efectueaza un transfer cu succes.
 */
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

    public SendMoneyTransaction(final TransactionData transactionData) {
        super(transactionData.getTimestamp());
        this.description = transactionData.getDescription();
        this.senderIBAN = transactionData.getSenderIban();
        this.receiverIBAN = transactionData.getReceiverIban();
        this.amount = transactionData.getSendMoneyAmount();
        this.transferType = transactionData.getTransferType();
    }
}
