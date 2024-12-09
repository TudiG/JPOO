package org.poo.associated.bankRelated;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.poo.associated.userRelated.User;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;

@Getter
public class Bank {
    private List<User> users;
    // private List<Transaction> transactions;
    private static Bank bankInstance;

    private Bank() {
        // this.transactions = new ArrayList<>();
    }

    public synchronized static Bank getInstance() {
        if (bankInstance == null) {
            bankInstance = new Bank();
        }

        return bankInstance;
    }

    public void addAllUsers(final List<User> users) {
        this.users = users;
    }

    public Account findAccountByIBAN(String iban) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .filter(account -> iban.equals(account.getIBAN()))
                .findFirst().orElse(null);
    }

    public Account findAccountByCardNumber(String cardNumber) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .filter(account -> account.getCards().stream()
                        .anyMatch(card -> cardNumber.equals(card.getCardNumber())))
                .findFirst()
                .orElse(null);
    }
 }
