package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Alias;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;

/**
 * Comanda care creeaza si stocheaza un alies creeat de un
 * utilizator.
 */
public final class SetAliasCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Account account = bank.findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            return;
        }

        Alias alias = new Alias(account.getBelongsToEmail(), account, commandInput.getAlias());
        bank.getAliasDatabase().put(account.getBelongsToEmail(), alias);
    }
}
