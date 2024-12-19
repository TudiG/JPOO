package org.poo.associated.userRelated.accounts.accountUtilities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.transactionRelated.commerciantReport.CommerciantReport;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa contine toate datele specifice unui cont ale
 * unui utilizator.
 */
@Getter
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
     * Metoda de adaugare de fonduri in balanta unui cont.
     *
     * @param amount banii care vor fi adaugati balantei curente.
     */
    public final void addFunds(final double amount) {
        balance += amount;
    }

    /**
     * Metoda de scadere de fonduri din balanta unui cont.
     *
     * @param amount banii care vor fi scazuti din balanta curent.
     */
    public final void subtractFunds(final double amount) {
        if (amount <= this.balance) {
            balance -= amount;
        }
    }

    /**
     * Metoda care actualizeaza balanta minima a unui cont.
     *
     * @param newMinimumBalance noua balanta minima asociata unui cont.
     */
    public final void updateMinimumBalance(final double newMinimumBalance) {
        this.minimumBalance = newMinimumBalance;
    }
}
