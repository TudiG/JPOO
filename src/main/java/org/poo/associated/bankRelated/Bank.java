package org.poo.associated.bankRelated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;
import org.poo.associated.userRelated.user.User;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;

@Getter
public final class Bank {
    private static Bank bankInstance;
    private List<User> users;
    private Map<String, Alias> aliasDatabase;
    private Map<String, List<Transaction>> userTransactionsDatabase;

    private Bank() {
        aliasDatabase = new HashMap<>();
        userTransactionsDatabase = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public static synchronized Bank getInstance() {
        if (bankInstance == null) {
            bankInstance = new Bank();
        }

        return bankInstance;
    }

    /**
     *
     * @param collectedUsers
     */
    public void addAllUsers(final List<User> collectedUsers) {
        this.users = collectedUsers;
    }

    /**
     *
     * @param cardNumber
     * @return
     */
    public User findUserByCardNumber(final String cardNumber) {
        return users.stream()
                .filter(user -> user.getAccounts().stream()
                        .flatMap(account -> account.getCards().stream())
                        .anyMatch(card -> cardNumber.equals(card.getCardNumber())))
                .findFirst()
                .orElse(null);
    }

    /**
     *
     * @param iban
     * @return
     */
    public Account findAccountByIBAN(final String iban) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .filter(account -> iban.equals(account.getIban()))
                .findFirst().orElse(null);
    }

    /**
     *
     * @param cardNumber
     * @return
     */
    public Account findAccountByCardNumber(final String cardNumber) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .filter(account -> account.getCards().stream()
                        .anyMatch(card -> cardNumber.equals(card.getCardNumber())))
                .findFirst()
                .orElse(null);
    }

    /**
     *
     * @param cardNumber
     * @return
     */
    public Card findCardByNumber(final String cardNumber) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .flatMap(account -> account.getCards().stream())
                .filter(card -> cardNumber.equals(card.getCardNumber()))
                .findFirst()
                .orElse(null);
    }
 }
