package org.poo.associated.userRelated;

import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

public class ClassicAccount extends Account {
    public ClassicAccount(final CommandInput commandInput) {
        super(Utils.generateIBAN(), commandInput.getCurrency(), commandInput.getAccountType());
    }
}
