package org.poo.associated.userRelated.transaction;

import lombok.Builder;

@Builder
public class Transaction {
    private Integer timestamp;
    private String description;
    private String card;
    private String cardHolder;
    private String account;
    private Double amount;
    private String commerciant;
    private String senderIBAN;
    private String receiverIBAN;
    private String transferType;
}
