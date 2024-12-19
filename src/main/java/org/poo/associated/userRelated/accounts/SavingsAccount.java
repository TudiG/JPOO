package org.poo.associated.userRelated.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

/**
 * Clasa care reprezinta contul de economii. Prezinta
 * un camp extra pentru dobanda asociata unui cont de economii.
 */
@Getter
public final class SavingsAccount extends Account {
    @JsonIgnore
    private double interestRate;

    public SavingsAccount(final CommandInput commandInput, final String belongsToEmail) {
        super(Utils.generateIBAN(), commandInput.getCurrency(),
                commandInput.getAccountType(), belongsToEmail);
        this.interestRate = commandInput.getInterestRate();
    }

    /**
     * Metoda pentru actualizarea dobanzii unui cont.
     *
     * @param newInterestRate noua dobanda a contului.
     */
    public void updateInterestRate(final double newInterestRate) {
        this.interestRate = newInterestRate;
    }
}
