package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;

/**
 * Comanda pentru actualizarea balantei minime a unui cont.
 */
public final class SetMinimumBalanceCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Account account = Bank.getInstance().findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            return;
        }

        account.updateMinimumBalance(commandInput.getAmount());
    }
}
