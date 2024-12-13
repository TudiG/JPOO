package org.poo.associated.userRelated.accounts.accountUtilities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.poo.associated.userRelated.card.Card;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JsonPropertyOrder({ "IBAN", "balance", "currency", "type", "cards" })
public abstract class Account {
    @JsonProperty("IBAN")
    protected String IBAN;
    protected double balance = 0;
    protected String currency;
    protected String type;
    protected List<Card> cards = new ArrayList<>();
    @JsonIgnore
    protected double minimumBalance = 0;
    @JsonIgnore
    protected String belongsToEmail;

    public Account(final String IBAN, final String currency, final String accountType, final String belongsToEmail) {
        this.IBAN = IBAN;
        this.currency = currency;
        this.type = accountType;
        this.belongsToEmail = belongsToEmail;
    }

    public void addFunds(final double amount) {
        this.balance += amount;
    }

    public void subtractFunds(final double amount) {
        if(amount <= this.balance) {
            this.balance -= amount;
        }
    }
}
