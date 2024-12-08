package org.poo.associated.bankRelated;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.poo.associated.userRelated.User;

@Getter
public class Bank {
    private List<User> users;
    private List<Transaction> transactions;
    private static Bank bankInstance;

    private Bank() {
        this.transactions = new ArrayList<>();
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
 }
