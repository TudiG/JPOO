package org.poo.associated.transactionRelated.transactionUtilities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class TransactionData {
    private int timestamp;
    private double amount;
    private double interestRate;
    private String cardNumber;
    private String email;
    private String account;
    private String commerciant;
    private String description;
    private String senderIban;
    private String receiverIban;
    List<String> involvedAccounts;
    private String failedAccount;
    private String currency;
}
