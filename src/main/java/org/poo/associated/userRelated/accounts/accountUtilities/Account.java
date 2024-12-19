package org.poo.associated.userRelated.accounts.accountUtilities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.userRelated.commerciantReport.CommerciantReport;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JsonPropertyOrder({ "IBAN", "balance", "currency", "type", "cards" })
public abstract class Account {
    @JsonProperty("IBAN")
    protected String iban;
    protected double balance = 0;
    protected String currency;
    protected String type;
    protected List<Card> cards;
    @JsonIgnore
    protected double minimumBalance = 0;
    @JsonIgnore
    protected String belongsToEmail;
    @JsonIgnore
    protected List<Transaction> accountTransactions;
    @JsonIgnore
    protected List<CommerciantReport> commerciantInteractions;

    public Account(final String iban,
                   final String currency,
                   final String accountType,
                   final String belongsToEmail) {
        this.iban = iban;
        this.currency = currency;
        this.type = accountType;
        this.belongsToEmail = belongsToEmail;
        cards = new ArrayList<>();
        accountTransactions = new ArrayList<>();
        commerciantInteractions = new ArrayList<>();
    }

    /**
     *
     * @param amount
     */
    public final void addFunds(final double amount) {
        balance += amount;
    }

    /**
     *
     * @param amount
     */
    public final void subtractFunds(final double amount) {
        if (amount <= this.balance) {
            balance -= amount;
        }
    }
}
