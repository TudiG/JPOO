package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bank.Bank;
import org.poo.associated.userRelated.Account;
import org.poo.associated.userRelated.AccountFactory;
import org.poo.fileio.CommandInput;

public class AddAccount implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Account account = AccountFactory.createAccount(commandInput);

        Bank.getInstance().getUsers().stream()
                .filter(user -> commandInput.getEmail().equals(user.getEmail()))
                .forEach(user -> user.getAccounts().add(account));
    }
}
