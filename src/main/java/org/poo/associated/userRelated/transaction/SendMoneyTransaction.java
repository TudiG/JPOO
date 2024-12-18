package org.poo.associated.userRelated.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMoneyTransaction extends Transaction {
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

    public SendMoneyTransaction(Integer timestamp, String description, String senderIBAN, String receiverIBAN, String amount, String transferType) {
        super(timestamp);
        this.description = description;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.transferType = transferType;
    }
}
