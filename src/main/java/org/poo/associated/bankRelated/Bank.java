package org.poo.associated.bankRelated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.userRelated.transaction.Transaction;
import org.poo.associated.userRelated.user.User;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;

@Getter
public final class Bank {
    private List<User> users;
    private static Bank bankInstance;
    private Map<String, Alias> aliasDatabase;

    // Se va folosi pentru noua implementare a tranzactiilor
    private Map<String, List<Transaction>> userTransactionsDatabase;

    private Bank() {
        aliasDatabase = new HashMap<>();
        userTransactionsDatabase = new HashMap<>();
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

    public User findUserByCardNumber(String cardNumber) {
        return users.stream()
                .filter(user -> user.getAccounts().stream()
                        .flatMap(account -> account.getCards().stream())
                        .anyMatch(card -> cardNumber.equals(card.getCardNumber())))
                .findFirst()
                .orElse(null);
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

    public Card findCardByNumber(String cardNumber) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .flatMap(account -> account.getCards().stream())
                .filter(card -> cardNumber.equals(card.getCardNumber()))
                .findFirst()
                .orElse(null);
    }
 }
