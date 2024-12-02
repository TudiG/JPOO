package org.poo.associated.userRelated;

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
