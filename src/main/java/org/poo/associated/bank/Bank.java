package org.poo.associated.bank;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.poo.associated.userRelated.User;

@Getter
public class Bank {
    private List<User> users;
    private List<Transaction> transactions;

    public Bank(List<User> users) {
        this.users = users;
        this.transactions = new ArrayList<>();
    }
 }
