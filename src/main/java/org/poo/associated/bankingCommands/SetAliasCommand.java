package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Alias;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;

public class SetAliasCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Account account = bank.findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            return;
        }

        Alias alias = new Alias(commandInput.getEmail(), account, commandInput.getAlias());

        bank.getAliasDatabase().put(commandInput.getAlias(), alias);
    }
}
