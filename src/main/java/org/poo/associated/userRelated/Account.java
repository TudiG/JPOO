package org.poo.associated.userRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class Account {
    @JsonProperty("IBAN")
    protected String IBAN;
    protected double balance;
    protected String currency;
    protected String type;
    protected List<Card> cards = new ArrayList<>();

    public Account(String IBAN, String currency, String accountType) {
        this.IBAN = IBAN;
        this.currency = currency;
        this.type = accountType;
    }

    public void addFund(double amount) {
        this.balance += amount;
    }
}
