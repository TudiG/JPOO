package org.poo.associated.userRelated.accounts.accountUtilities;

import org.poo.associated.userRelated.accounts.ClassicAccount;
import org.poo.associated.userRelated.accounts.SavingsAccount;
import org.poo.fileio.CommandInput;

public final class AccountFactory {
    private AccountFactory() { }

    /**
     * Metoda aceasta implementeaza un factory foarte simplu pentru
     * instantierea unui cont nou, care poate sa fie clasic sau de economii.
     *
     * @param commandInput contine toate datele necesare unui cont.
     * @param belongsToEmail email-ul asociat contului.
     * @return
     */
    public static Account createAccount(final CommandInput commandInput,
                                        final String belongsToEmail) {
        return switch (commandInput.getAccountType()) {
            case "classic" -> new ClassicAccount(commandInput, belongsToEmail);
            case "savings" -> new SavingsAccount(commandInput, belongsToEmail);
            default -> throw new IllegalArgumentException("Invalid account type");
        };
    }
}
