package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.commandService.commandUtilities.StaticOutputs;
import org.poo.associated.userRelated.accounts.SavingsAccount;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;

/**
 * Comanda pentru a adauga dobanda unui cont de economii.
 * Aceasta verifica daca contul este un cont de economii si, daca da, aplica dobanda.
 * In caz contrar, se afiseaza un mesaj de eroare.
 */
public final class AddInterestCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Account account = Bank.getInstance().findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            return;
        }

        if (account.getClass().equals(SavingsAccount.class)) {
            account.addFunds(account.getBalance() * ((SavingsAccount) account).getInterestRate());
        } else {
            StaticOutputs.isNotSavingsAccount(commandInput, output);
        }
    }
}
