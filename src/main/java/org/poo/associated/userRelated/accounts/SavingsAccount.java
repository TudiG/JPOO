package org.poo.associated.userRelated.accounts;

import lombok.Getter;
import lombok.Setter;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

@Getter @Setter
public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(final CommandInput commandInput) {
        super(Utils.generateIBAN(), commandInput.getCurrency(), commandInput.getAccountType());
        this.interestRate = commandInput.getInterestRate();
    }
}
