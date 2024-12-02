package org.poo.associated.bank;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.poo.associated.userRelated.User;

@Getter
public class Bank {
    private List<User> users;
    private List<Transaction> transactions;
    private static Bank bankInstance;

    private Bank(List<User> users) {
        this.transactions = new ArrayList<>();
    }

    public static Bank getInstance() {
        if(bankInstance == null) {
            bankInstance = new Bank(new ArrayList<>());
        }

        return bankInstance;
    }

    public void addAllUsers(List<User> users) {
        this.users = users;
    }
 }
