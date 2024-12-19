package org.poo.associated.transactionRelated.transactionUtilities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Aceasta clasa este un intermediar necesar pentru
 * pasarea datelor folosite la instantierea unei tranzactii.
 * Este implementata cu Builder din pachetul lombok.
 */
@Builder
@Getter
@AllArgsConstructor
public final class TransactionData {
    private int timestamp;
    private double amount;
    private double interestRate;
    @Setter
    private String cardNumber;
    private String email;
    private String account;
    private String commerciant;
    private String description;
    private String senderIban;
    private String receiverIban;
    @Setter
    private String sendMoneyAmount;
    @Setter
    private String transferType;
    private List<String> involvedAccounts;
    private String currency;
}
