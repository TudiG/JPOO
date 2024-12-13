package org.poo.associated.userRelated.accounts;

import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

public final class ClassicAccount extends Account {
    public ClassicAccount(final CommandInput commandInput, final String belongsToEmail) {
        super(Utils.generateIBAN(), commandInput.getCurrency(), commandInput.getAccountType(), belongsToEmail);
    }
}
