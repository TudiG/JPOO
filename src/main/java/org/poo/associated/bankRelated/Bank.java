package org.poo.associated.bankRelated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import org.poo.associated.userRelated.User;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;

@Getter
public class Bank {
    private List<User> users;
    private static Bank bankInstance;
    private Map<String, ArrayNode> transactionDatabase;
    private Map<String, Alias> aliasDatabase;

    private Bank() {
        transactionDatabase = new HashMap<>();
        aliasDatabase = new HashMap<>();
        // TODO
        // users = new ArrayList<>();
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

    public User findUserByEmail(String email) {
        return users.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst().orElse(null);
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
