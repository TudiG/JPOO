package org.poo.associated.userRelated.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

@Getter @Setter
public final class SavingsAccount extends Account {
    @JsonIgnore
    private double interestRate;

    public SavingsAccount(final CommandInput commandInput, final String belongsToEmail) {
        super(Utils.generateIBAN(), commandInput.getCurrency(),
                commandInput.getAccountType(), belongsToEmail);
        this.interestRate = commandInput.getInterestRate();
    }
}
