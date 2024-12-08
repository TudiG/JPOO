package org.poo.associated.userRelated.accounts.accountUtilities;

import org.poo.associated.userRelated.accounts.ClassicAccount;
import org.poo.associated.userRelated.accounts.SavingsAccount;
import org.poo.fileio.CommandInput;

public class AccountFactory {
    public static Account createAccount(CommandInput commandInput) {
        return switch(commandInput.getAccountType()) {
            case "classic" -> new ClassicAccount(commandInput);
            case "savings" -> new SavingsAccount(commandInput);
            default -> throw new IllegalArgumentException("Invalid account type");
        };
    }
}
